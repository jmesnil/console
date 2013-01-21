package org.jboss.mbui.model.structure;

import org.jboss.mbui.model.behaviour.Behaviour;
import org.jboss.mbui.model.structure.impl.InteractionUnitVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * A dialog contains a set of hierarchically structured abstract interaction objects,
 * which enable the execution of an interactive task.
 *
 * @author Heiko Braun
 * @date 1/16/13
 */
public class Dialog {
    private QName id;
    private InteractionUnit root;
    private Set<Behaviour> behaviours = new HashSet<Behaviour>();

    public Dialog(QName id, InteractionUnit root) {
        this.id = id;
        this.root = root;
    }

    public QName getId() {
        return id;
    }

    public InteractionUnit getInterfaceModel() {
        return root;
    }

    public Set<Behaviour> getBehaviourModel() {
        return behaviours;
    }

    public InteractionUnit findUnit(final QName id) {

        final Result result = new Result();

        InteractionUnitVisitor findById = new InteractionUnitVisitor() {

            @Override
            public void startVisit(Container container) {
                if (container.getId().equals(id))
                    result.setUnit(container);
            }

            @Override
            public void visit(InteractionUnit interactionUnit) {
                if (interactionUnit.getId().equals(id))
                    result.setUnit(interactionUnit);
            }

            @Override
            public void endVisit(Container container) {

            }
        };

        root.accept(findById);

        if(null==result.getUnit())
            System.out.println("No interaction unit with id "+ id);

        return result.getUnit();
    }

    class Result {
        InteractionUnit unit;

        public InteractionUnit getUnit() {
            return unit;
        }

        public void setUnit(InteractionUnit unit) {
            this.unit = unit;
        }
    }

}
