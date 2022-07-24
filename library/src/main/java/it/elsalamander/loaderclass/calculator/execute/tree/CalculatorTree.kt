package it.elsalamander.loaderclass.calculator.execute.tree

import it.elsalamander.loaderclass.calculator.execute.operator.Operator

/**
 * Mappa per eseguire le operazioni
 * Incomincio con un nodo
 * Si crea la radice, che inizialmente contiene il primo numero o operazione
 * poi si dirama secondo le regole:
 * - Se l'operazione ha priorità piu alta mi metto nel figlio
 * - Se l'operazione ha priorità più bassa vado sul padre, cerco il nodo
 *   con la stessa priorità ma più in "alto" possibile finche non trovo
 *   o la radice o un nodo con priorità piu bassa
 * - Il nodo può contenere o una Operanzione da eseguire o un numero, i numeri
 *   sono sempre una foglia in quanto non volgiono parametri ovvero figli
 *   i nodi interni sono sempre Operazioni, tali operazioni sono descritti dalla
 *   operation.
 *
 * Il put quindi è dato da:
 * - Numero
 * - Operazione: in questo caso viene tenuto conto la priorità per l'inserimento
 */
class CalculatorTree {

    var root : Node? = null
    var currentNode : Node? = null

    fun add(num : Double){
        currentNode = if(currentNode != null){
            currentNode?.dx = Node(num, currentNode!!)
            currentNode?.dx
        }else{
            root = Node(num, null)
            root
        }
    }

    fun add(operator: Operator){
        //se la radice è null significa che è un operator a un parametro
        currentNode = if(currentNode == null){ //equivale a root == null
            root = Node(operator, null)
            root
        }else{
            //ci sono 2 casi
            //un operatore che sta dopo un operatore esempio "ln"
            //un operatore che sta dopo un numero
            //poichè un al termine di un inserimento di un operatore
            //ritorno il nodo con l'operatore e figlio dx null basta
            //controllare se il figlio dx è null
            if(currentNode!!.dx == null && !currentNode!!.hasNumber){
                //sto inserendo un'altro operatore di seguito
                currentNode!!.dx = Node(operator, currentNode)
                currentNode!!.dx
            }else{
                if(currentNode!!.padre == null){
                    val tmp = Node(operator, null)
                    tmp.sx = currentNode
                    root = tmp
                    root
                }else{
                    //ho due casi
                    //prio padre < dell'inserito
                    //prio padre > dell'inserito
                    if(currentNode!!.padre!!.op!!.prio <= operator.prio){
                        //inserisco al figlio destro
                        val tmp = Node(operator, currentNode!!.padre)
                        currentNode!!.padre!!.dx = tmp
                        currentNode!!.padre = tmp
                        tmp.sx = currentNode
                        tmp
                    }else{
                        //salgo di nodo finche non trovo un nodo la quale operazione
                        //ha una priorità più bassa
                        var tmp = currentNode!!.padre
                        while(tmp!!.padre != null && tmp.padre!!.op!!.prio > operator.prio){
                            tmp = tmp.padre
                        }
                        //se non esiste un nodo con priorità piu bassa trovero
                        if(tmp.padre == null && tmp.op!!.prio > operator.prio){
                            tmp.padre = Node(operator, null)
                            root = tmp.padre
                        }else{
                            //ho il nodo desiderato
                            //inserisci il nodo come figlio destro al padre
                            tmp.padre!!.dx = Node(operator, tmp.padre)
                            tmp.padre = tmp.padre!!.dx
                        }
                        tmp.padre!!.sx = tmp
                        tmp.padre
                    }
                }
            }
        }
    }

    fun solve() : Double{
        return this.root?.getNumber() ?: 0.0
    }
}