package com.example.demo.kotest.common

import com.example.demo.kotest.common.security.SecurityListenerFactory
import com.example.demo.utils.SwaggerUtils
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

abstract class BaseIntegrationController : BehaviorSpec() {
  @Autowired
  protected lateinit var webApplicationContext: WebApplicationContext

  @Autowired
  protected lateinit var mockMvc: MockMvc

  @Autowired
  protected lateinit var objectMapper: ObjectMapper

  @MockkBean
  private lateinit var swaggerUtils: SwaggerUtils

  /**
   * ResponseAdvice Status
   */
  protected val commonStatus: Int = HttpStatus.OK.value()

  /**
   * ResponseAdvice Message
   */
  protected val commonMessage: String = HttpStatus.OK.name

  fun initialize() {
    listeners(SecurityListenerFactory())

    beforeSpec {
      mockMvc =
        MockMvcBuilders
          .webAppContextSetup(webApplicationContext)
          .apply<DefaultMockMvcBuilder>(SecurityMockMvcConfigurers.springSecurity())
          .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
          .build()
    }

    beforeTest {
      every {
        swaggerUtils.confirmPathEqualsSwaggerConfig(any<String>())
      } returns false
    }
  }
}
