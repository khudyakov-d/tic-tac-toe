package ccfit.nsu.ru.khudyakov.tic_tok_toe.player_selector;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.new_game_menu.NewGameMenuActivity;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.new_game_menu.NewGamePresenter;

public class PlayerSelectorFragment extends Fragment implements PlayerSelectorView {

    SelectPlayerListener selectPlayerListener;

    public PlayerSelectorFragment() {
    }

    public static Fragment newInstance() {
        return new PlayerSelectorFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectPlayerListener = (SelectPlayerListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.players_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NewGamePresenter newGamePresenter = ((NewGameMenuActivity) Objects.requireNonNull(getActivity())).getNewGamePresenter();

        RecyclerView recyclerView = view.findViewById(R.id.players_list);
        PlayerSelectorAdapter playerSelectorAdapter = new PlayerSelectorAdapter(getContext(), this::selectPlayer);

        playerSelectorAdapter.setPlayers(newGamePresenter.getPlayers());
        recyclerView.setAdapter(playerSelectorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void selectPlayer(Player player) {
        selectPlayerListener.onPlayerSelect(player, getId());
    }

    public interface SelectPlayerListener {
        void onPlayerSelect(Player player, int fragmentId);
    }
}
