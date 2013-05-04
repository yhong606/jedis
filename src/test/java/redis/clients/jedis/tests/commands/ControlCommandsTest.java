package redis.clients.jedis.tests.commands;

import java.util.List;

import org.junit.Test;

import redis.clients.jedis.DebugParams;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisMonitor;
import redis.clients.jedis.exceptions.JedisDataException;

public class ControlCommandsTest extends JedisCommandTestBase {
    @Test
    public void save() {
        String status = jedis.save();
        assertEquals("OK", status);
    }

    @Test
    public void bgsave() {
        try {
            String status = jedis.bgsave();
            assertEquals("Background saving started", status);
        } catch (JedisDataException e) {
            assertTrue("ERR Background save already in progress"
                    .equalsIgnoreCase(e.getMessage()));
        }
    }

    @Test
    public void bgrewriteaof() {
    	String scheduled = "Background append only file rewriting scheduled";
        String started = "Background append only file rewriting started";
        
        String status = jedis.bgrewriteaof();
        
        boolean ok = status.equals(scheduled) || status.equals(started);  
        assertTrue(ok);
    }

    @Test
    public void lastsave() throws InterruptedException {
        long before = jedis.lastsave();
        String st = "";
        while (!st.equals("OK")) {
            try {
                Thread.sleep(1000);
                st = jedis.save();
            } catch (JedisDataException e) {

            }
        }
        long after = jedis.lastsave();
        assertTrue((after - before) > 0);
    }

    @Test
    public void info() {
        String info = jedis.info();
        assertNotNull(info);
        System.out.println(info);
        info = jedis.info("server");
        assertNotNull(info);
    }

    @Test
    public void monitor() {
        new Thread(new Runnable() {
            public void run() {
                Jedis j = new Jedis("192.168.49.130");
                j.auth("foobared");
                for (int i = 0; i < 4; i++) {
                    j.incrBy("foobared",i);
                }
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                }
                j.incr("foobared");
                j.disconnect();
            }
        }).start();

        jedis.monitor(new JedisMonitor() {
            private int count = 0;

            public void onCommand(String command) {
            	System.out.println("调用命令:"+command);
//                if (command.contains("INCR")) {
//                    count++;
//                }
//                if (count == 5) {
//                    client.disconnect();
//                }
            }
        });
    }

    @Test
    public void configGet() {
        List<String> infos = jedis.configGet("*");
        for (String info : infos) {
			System.out.println(info);
		}
        assertNotNull(infos);
    }

    @Test
    public void configSet() {
        List<String> info = jedis.configGet("maxmemory");
        String memory = info.get(1);
        String status = jedis.configSet("maxmemory", "200");
        assertEquals("OK", status);
        jedis.configSet("maxmemory", memory);
    }

    @Test
    public void sync() {
        jedis.sync();
    }

    @Test
    public void debug() {
        jedis.set("foo", "bar");
        String resp = jedis.debug(DebugParams.OBJECT("foo"));
        assertNotNull(resp);
        System.out.println(resp);
        resp = jedis.debug(DebugParams.RELOAD());
        assertNotNull(resp);
        System.out.println(resp);
    }
}