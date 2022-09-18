package com.example.starwars.common.exception

import com.example.starwars.common.viewstate.ViewError
import java.lang.Exception

data class ViewErrorException(val viewError: ViewError) : Exception()