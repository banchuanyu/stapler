package org.kohsuke.stapler.export;

import com.google.common.base.Predicate;

/**
 * Decorates a base {@link TreePruner} by also refusing properties
 * that are present in the given {@link Model}.
 *
 * @author Kohsuke Kawaguchi
 */
class FilteringTreePruner extends TreePruner {
    private final Predicate<String> predicate;
    private final TreePruner base;

    FilteringTreePruner(Predicate<String> predicate, TreePruner base) {
        this.predicate = predicate;
        this.base = base;
    }

    @Override
    public TreePruner accept(Object node, Property prop) {
        if (predicate.apply(prop.name))
            return null;
        TreePruner child = base.accept(node, prop);

        // for merge properties, the current restrictions on the property names should
        // still apply to the child TreePruner
        if (prop.merge)
            child = new FilteringTreePruner(predicate,child);

        return child;
    }
}
