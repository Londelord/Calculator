package ru.rakhmanovsaid.lb4

import kotlin.math.*

object MathParser {
    private val operators = setOf('+', '-', '*', '/', '%')

    fun evaluate(expression: String): Double {
        val preparedExpression = prepareExpression(expression)
        val tokens = tokenize(preparedExpression)
        val postfix = infixToPostfix(tokens)
        return calculatePostfix(postfix)
    }

    private fun prepareExpression(expression: String): String {
        return expression
            .replace("π", PI.toString()) // Замена символа π на его числовое значение
            .replace("√", "sqrt")        // Замена символа корня
            .replace("×", "*")           // Замена символа умножения
            .replace("÷", "/")           // Замена символа деления
            .replace("e", E.toString())  // Замена символа e на его числовое значение
    }

    private fun tokenize(expression: String): List<String> {
        // Регулярное выражение для разбора чисел, операторов и функций
        val regex = Regex("([+\\-*/%()])|([0-9]+(?:\\.[0-9]+)?)|sqrt|e")
        return regex.findAll(expression).map { it.value }.toList()
    }

    private fun infixToPostfix(tokens: List<String>): List<String> {
        val output = mutableListOf<String>()
        val operatorStack = mutableListOf<String>()

        val precedence = mapOf(
            "+" to 1, "-" to 1,
            "*" to 2, "/" to 2, "%" to 2,
            "sqrt" to 3
        )

        for (token in tokens) {
            when {
                token.matches(Regex("\\d+\\.?\\d*")) -> output.add(token)
                token == "sqrt" -> operatorStack.add(token)
                token == "(" -> operatorStack.add(token)
                token == ")" -> {
                    while (operatorStack.isNotEmpty() && operatorStack.last() != "(") {
                        output.add(operatorStack.removeAt(operatorStack.size - 1))
                    }
                    if (operatorStack.isNotEmpty() && operatorStack.last() == "(") {
                        operatorStack.removeAt(operatorStack.size - 1)
                    }
                }
                operators.contains(token.first()) -> {
                    while (operatorStack.isNotEmpty() && (precedence[operatorStack.last()]
                            ?: 0) >= precedence[token]!!
                    ) {
                        output.add(operatorStack.removeAt(operatorStack.size - 1))
                    }
                    operatorStack.add(token)
                }
            }
        }

        while (operatorStack.isNotEmpty()) {
            output.add(operatorStack.removeAt(operatorStack.size - 1))
        }

        return output
    }

    private fun calculatePostfix(postfix: List<String>): Double {
        val stack = mutableListOf<Double>()

        for (token in postfix) {
            when {
                token.matches(Regex("\\d+\\.?\\d*")) -> stack.add(token.toDouble())
                token == "+" -> stack.add(stack.removeAt(stack.size - 2) + stack.removeAt(stack.size - 1))
                token == "-" -> stack.add(stack.removeAt(stack.size - 2) - stack.removeAt(stack.size - 1))
                token == "*" -> stack.add(stack.removeAt(stack.size - 2) * stack.removeAt(stack.size - 1))
                token == "/" -> stack.add(stack.removeAt(stack.size - 2) / stack.removeAt(stack.size - 1))
                token == "%" -> stack.add(stack.removeAt(stack.size - 2) % stack.removeAt(stack.size - 1))
                token == "sqrt" -> stack.add(sqrt(stack.removeAt(stack.size - 1)))
                token == "e" -> stack.add(E)  // Число e
            }
        }

        return stack.first()
    }
}