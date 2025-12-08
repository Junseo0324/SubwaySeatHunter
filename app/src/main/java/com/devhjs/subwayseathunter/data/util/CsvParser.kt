package com.devhjs.subwayseathunter.data.util

import java.io.InputStream

interface CsvParser<T> {
    fun parse(inputStream: InputStream): List<T>
}
