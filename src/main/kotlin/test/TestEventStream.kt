package test

import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.core.http.HttpClientOptions
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions


object TestEventStream {

    @JvmStatic
    fun main(args: Array<String>) {
        val vertx = Vertx.vertx()
        val opts = HttpClientOptions()
        val client = vertx.createHttpClient(opts)

        client.get("stream.meetup.com", "/2/rsvps") { resp ->
            println(resp.statusCode())
            var receiving = Buffer.buffer()
            resp.handler { buff ->
                val str = buff.toString("UTF-8")
                receiving.appendBuffer(buff)
                if (receiving.toString().endsWith("\n")) {
                    val json = JsonObject(receiving)
                    println(json)
                    receiving = Buffer.buffer()
                }
            }
        }
        .setChunked(true)
        .putHeader("accept", "text/event-stream")
        .end()
    }

}
