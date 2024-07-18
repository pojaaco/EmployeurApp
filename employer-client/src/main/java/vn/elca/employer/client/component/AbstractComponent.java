package vn.elca.employer.client.component;

import javafx.scene.Node;
import javafx.util.Pair;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.components.managedFragment.ManagedFragmentHandler;
import org.jacpfx.rcp.context.Context;
import vn.elca.employer.client.fragment.AbstractFragment;

public abstract class AbstractComponent implements FXComponent {
    public <T extends AbstractFragment> Pair<T, Node> registerFragment(Context context, Class<T> fragmentType) {
        ManagedFragmentHandler<T> fragment = context.getManagedFragmentHandler(fragmentType);
        fragment.getController().init();
        return new Pair<>(fragment.getController(), fragment.getFragmentNode());
    }
}
