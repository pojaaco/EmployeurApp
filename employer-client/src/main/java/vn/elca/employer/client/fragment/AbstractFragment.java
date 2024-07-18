package vn.elca.employer.client.fragment;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.rcp.components.managedFragment.ManagedFragmentHandler;
import org.jacpfx.rcp.context.Context;

@Fragment(id = AbstractFragment.ID)
public class AbstractFragment {
    public static final String ID = "AbstractFragment";

    @Resource
    private Context context;

    public <T extends CustomFragment> Pair<T, Node> registerFragment(Class<T> fragmentType) {
        ManagedFragmentHandler<T> fragment = context.getManagedFragmentHandler(fragmentType);
        fragment.getController().init();
        return new Pair<>(fragment.getController(), new Pane());
    }
}