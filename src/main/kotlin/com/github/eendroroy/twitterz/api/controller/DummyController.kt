package com.github.eendroroy.twitterz.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

/**
 *
 * @author indrajit
 */

@RestController
@RequestMapping(path = ["dummy"])
class DummyController {
    @GetMapping("")
    @ResponseBody
    fun register(@RequestBody body: Map<String, String>, response: HttpServletResponse): Map<String, Any> {
        return mutableMapOf("success" to true)
    }
}
