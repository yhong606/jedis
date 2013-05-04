package redis.clients.jedis.tests;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.tests.commands.JedisCommandTestBase;
import redis.clients.util.SafeEncoder;

public class JedisTest extends JedisCommandTestBase {
	/*@Test
	public void useWithoutConnecting() {
		Jedis jedis = new Jedis("192.168.49.131");
		jedis.auth("foobared");
		Long dbSize = jedis.dbSize();
		System.out.println("dbSize:"+dbSize);
	}*/

	/*@Test
	public void checkBinaryData() {
		byte[] bigdata = new byte[1777];
		for (int b = 0; b < bigdata.length; b++) {
			bigdata[b] = (byte) ((byte) b % 255);
		}
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("data", SafeEncoder.encode(bigdata));

		String status = jedis.hmset("foo", hash);
		assertEquals("OK", status);
		assertEquals(hash, jedis.hgetAll("foo"));
	}*/

	/*@Test
	public void connectWithShardInfo() {
		JedisShardInfo shardInfo = new JedisShardInfo("192.168.49.131", Protocol.DEFAULT_PORT);
//		shardInfo.setPassword("foobared");
		Jedis jedis = new Jedis(shardInfo);
		String string = jedis.get("foo");
		System.out.println(string);
	}*/

	/*@Test(expected = JedisConnectionException.class)
	public void timeoutConnection() throws Exception {
		jedis = new Jedis("localhost", 6379, 15000);
		jedis.auth("foobared");
		jedis.configSet("timeout", "1");
		// we need to sleep a long time since redis check for idle connections
		// every 10 seconds or so
		Thread.sleep(20000);
		jedis.hmget("foobar", "foo");
	}*/

	/*@Test //(expected = JedisDataException.class)
	public void failWhenSendingNullValues() {
//		jedis.set("foo", "abnc");
		jedis.select(1);
		String string = jedis.set("foo","111");
		System.out.println(string);
	}
*/
	/*@Test
	public void shouldReconnectToSameDB() throws IOException {
		jedis.select(1);
		jedis.set("foo", "bar2");
		jedis.getClient().getSocket().shutdownInput();
		jedis.getClient().getSocket().shutdownOutput();
		assertEquals("bar2", jedis.get("foo"));
	}*/
	/*@Test
	public void testtest(){
		jedis.select(1);
		String string = jedis.get("foo");
		System.out.println(string);
	}*/
	@Test
	public void startWithUrlString() {
		Jedis j = new Jedis("192.168.49.130", 6379);
		j.auth("foobared");
//		j.select(1);
		j.set("foo", "bar");
//		Jedis jedis = new Jedis("redis://:foobared@localhost:6380/2");
		assertEquals("PONG", jedis.ping());
		assertEquals("bar", jedis.get("foo"));
	}

	/*@Test
	public void startWithUrl() throws URISyntaxException {
		Jedis j = new Jedis("localhost", 6380);
		j.auth("foobared");
		j.select(2);
		j.set("foo", "bar");
		Jedis jedis = new Jedis(new URI("redis://:foobared@localhost:6380/2"));
		assertEquals("PONG", jedis.ping());
		assertEquals("bar", jedis.get("foo"));
	}*/
}