package ccfit.nsu.ru.khudyakov.tic_tok_toe.player_selection;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game_menu.GameMenuActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.game_menu.GamePresenter;

public class PlayerSelectorFragment extends Fragment implements PlayerSelectorView {

    private final static String HEADER_TEXT = "HEADER_TEXT";

    SelectPlayerListener selectPlayerListener;

    public PlayerSelectorFragment() {
    }

    public static Fragment newInstance(String headerText) {
        Fragment fragment = new PlayerSelectorFragment();
        Bundle args = new Bundle();
        args.putString(HEADER_TEXT, headerText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.players_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initList(view);

        TextView textView = view.findViewById(R.id.player_list_text);
        textView.setText(requireArguments().getString(HEADER_TEXT));
    }

    private void initList(@NonNull View view) {
        GamePresenter gamePresenter = ((GameMenuActivity) requireActivity()).getGamePresenter();

        PlayerSelectorAdapter playerSelectorAdapter = new PlayerSelectorAdapter(getContext(), this::selectPlayer);
        playerSelectorAdapter.setPlayers(gamePresenter.getPlayers());

        final RecyclerView recyclerView = view.findViewById(R.id.players_list);
        recyclerView.setAdapter(playerSelectorAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);
    }

    public void setListener(SelectPlayerListener selectPlayerListener) {
        this.selectPlayerListener = selectPlayerListener;
    }

    @Override
    public void selectPlayer(Player player) {
        selectPlayerListener.onPlayerSelect(player);
    }

    public interface SelectPlayerListener {
        void onPlayerSelect(Player player);
    }
}
