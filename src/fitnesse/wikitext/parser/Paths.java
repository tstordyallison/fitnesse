package fitnesse.wikitext.parser;

import java.util.ArrayList;
import java.util.List;

public class Paths {
    private Translator translator;

    public Paths(Translator translator) {
        this.translator = translator;
    }

    public List<String> getPaths(Symbol syntaxTree) {
        TreeWalker walker = new TreeWalker();
        syntaxTree.walkPostOrder(walker);
        return walker.result;
    }

    private class TreeWalker implements SymbolTreeWalker {
        public List<String> result = new ArrayList<String>();

        public boolean visit(Symbol node) {
            if (node.getType() instanceof PathsCollector) {
                result.addAll(((PathsCollector) node.getType()).collectPaths(translator, node));
            } else if (node.isType(Path.symbolType)) {
                // backward compatibility with external extensions
                result.add(translator.translate(node.childAt(0)));
            }
            return true;
        }

        public boolean visitChildren(Symbol node) { return true; }
    }
}
