package ccfit.nsu.ru.khudyakov.tic_tok_toe.player_selection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.utils.Utils;

public class PlayerSelectorAdapter extends RecyclerView.Adapter<PlayerSelectorAdapter.PlayerHolder> {

    private final Context context;
    private final PlayerSelectorFragment.SelectPlayerListener selectPlayerListener;

    private final List<Player> players = new ArrayList<>();

    public PlayerSelectorAdapter(Context context, PlayerSelectorFragment.SelectPlayerListener selectPlayerListener) {
        this.context = context;
        this.selectPlayerListener = selectPlayerListener;
    }

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(context).inflate(R.layout.player_item, parent, false);
        return new PlayerSelectorAdapter.PlayerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerHolder holder, int position) {
        holder.bind(players.get(position));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void setPlayers(List<Player> players) {
        this.players.clear();
        this.players.addAll(players);
        notifyDataSetChanged();
    }

    class PlayerHolder extends RecyclerView.ViewHolder {

        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(final Player player) {
            ((TextView) itemView.findViewById(R.id.player_nickname_text)).setText(player.getNickname());
            Utils.uploadImage(context, (ImageView) itemView.findViewById(R.id.player_avatar_image), player.getImageUri());
            itemView.setOnClickListener(v -> selectPlayerListener.onPlayerSelect(player));
        }
    }

}
