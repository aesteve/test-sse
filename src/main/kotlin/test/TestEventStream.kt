package test

import io.vertx.core.Vertx
import io.vertx.core.http.HttpClientOptions
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
            resp.handler { buff ->
                println(buff)
            }
        }
        .setChunked(true)
        .putHeader("accept", "text/event-stream")
        .end()
    }

}
