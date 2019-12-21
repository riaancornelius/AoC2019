package com.riaancornelius.aoc2019.collection

class Queue<E> (list:MutableList<E> = mutableListOf()){

    private var items: MutableList<E> = list

    fun isEmpty():Boolean = items.isEmpty()

    fun size():Int = items.count()

    override  fun toString() = items.toString()

    fun enqueue(element:E){
        items.add(element)
    }

    fun enqueue(elements:Collection<E>){
        items.addAll(elements)
    }

    fun dequeue():E?{
        return if (this.isEmpty()){
            null
        } else {
            items.removeAt(0)
        }
    }

    fun peek():E?{
        return if (size() > 0) {
            items[0]
        } else {
            null
        }
    }
}

fun main() {
    val queue = Queue(mutableListOf("karthiq",2,3,"four"))

    //add 7 to Queue
    println(queue.enqueue(7))
    println(queue)//prints all elements
    //remove from Queue
    println(queue.dequeue())
    println(queue)//prints all elements present
    // fetch what is first element
    println(queue.peek())
    println(queue)
    // print the number of elements
    println(queue.size())
    // check whether Queue is empty
    println(queue.isEmpty())
}