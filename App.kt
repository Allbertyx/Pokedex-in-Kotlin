package com.example.project

import io.kvision.*
import io.kvision.core.*
import io.kvision.html.Align
import io.kvision.html.ListTag
import io.kvision.html.div
import io.kvision.html.image
import io.kvision.panel.root
import io.kvision.rest.RestClient
import io.kvision.rest.call
import io.kvision.rest.callDynamic
import io.kvision.table.*
import io.kvision.utils.Serialization.toObj
import io.kvision.utils.`in`
import io.kvision.utils.obj
import io.kvision.utils.px
import kotlinext.js.getOwnPropertyNames
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.StructureKind
import kotlin.js.Promise
import io.kvision.rest.*
import io.kvision.snabbdom.init
import io.kvision.utils.auto
import org.w3c.dom.css.CSSMarginRule

class App : Application() {
    init{
        require("C:/Users/Alberto/Documents/Programacion/Kotlin/Pokede/node_modules/fomantic-ui/dist/jquery-3.4.1.min.js")
        require("C:/Users/Alberto/Documents/Programacion/Kotlin/Pokede/node_modules/fomantic-ui/dist/semantic.min.js")
        require("C:/Users/Alberto/Documents/Programacion/Kotlin/Pokede/node_modules/fomantic-ui/dist/semantic.min.css")

    }
    override fun start() {
        val pokemons = downloadPokemons()
        val myStyle = Style {
            border = Border(1.px, BorderStyle.SOLID, Color.name(Col.GRAY))
            width = 0.px
            height = 50.px
            margin = 10.px
        }
        val myWidth = Style{ width = 97.px }
        val myBorder = Style{ border = Border(0.px, BorderStyle.NONE, Color.name(Col.WHITE)) }
        val myMargin = Style{ marginLeft = 20.px}
        val myBorderImg = Style{
            borderRadius = 5.px
            background = Background(Color("Gray"))
        }
        root("card") {
            div {
                table(
                    listOf("")
                ) {
                    pokemons.then {
                        for (i in 0..149) {
                            addCssStyle(myBorder)
                            row {
                                cell {
                                    addCssStyle(myBorder)
                                    addCssStyle(myStyle)
                                    div {
                                        addCssStyle(myWidth)
                                        addCssClass("card")
                                        div {
                                            image("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${i + 1}.png") {
                                                addCssClass("image")
                                                addCssStyle(myBorderImg)
                                            }
                                            addCssClass("content")
                                            div("${it.results.get(i).name}") {
                                                addCssClass("Header")
                                                addCssStyle(myMargin)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    }
fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        CoreModule
    )
}

@Serializable
data class PokemonPath(val name: String?, val url: String)

@Serializable
data class Repository(val count: Int, val next: String?, val previous: String?, val results: List<PokemonPath> )

private fun downloadPokemons():Promise<Repository>{
    return RestClient().call("https://pokeapi.co/api/v2/pokemon?limit=150&offset=0")
}

