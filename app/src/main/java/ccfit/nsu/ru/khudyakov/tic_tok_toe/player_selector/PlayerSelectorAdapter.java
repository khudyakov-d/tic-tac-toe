package ccfit.nsu.ru.khudyakov.tic_tok_toe.player_selector;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ccfit.nsu.ru.khudyakov.tic_tok_toe.R;
import ccfit.nsu.ru.khudyakov.tic_tok_toe.entities.Player;

public class PlayerSelectorAdapter extends RecyclerView.Adapter<PlayerSelectorAdapter.PlayerHolder> {

    private final Context context;
    private final PlayerSelectorAdapter.SelectPlayerListener selectPlayerListener;

    private List<Player> players = new ArrayList<>();

    public PlayerSelectorAdapter(Context context, PlayerSelectorAdapter.SelectPlayerListener selectPlayerListener) {
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

            if (player.getImageUri() != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), player.getImageUri());
                    ((ImageView) itemView.findViewById(R.id.player_avatar_image)).setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            itemView.setOnClickListener(v -> selectPlayerListener.onPlayerSelect(player));
        }
    }

    public interface SelectPlayerListener {
        void onPlayerSelect(Player player);
    }
}
