package domino

class DominoTreeNode(var value: Domino, var location: DominoLocation) {
    var parent: DominoTreeNode? = null
    var children = mutableListOf<DominoTreeNode>()

    fun addChild(node: DominoTreeNode){
        children.add(node)
        node.parent = this
    }

    override fun toString(): String {
        var s = "$value"
        if(children.isNotEmpty()){
            s += " {" + children.map { it.toString() } + " }"
        }
        return s
    }
}