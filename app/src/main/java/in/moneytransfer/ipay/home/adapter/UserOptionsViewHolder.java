package in.moneytransfer.ipay.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import in.moneytransfer.ipay.R;
import in.moneytransfer.ipay.home.model.UserOptions;

/**
 * Created by raghav on 13/08/17.
 */

public class UserOptionsViewHolder extends RecyclerView.ViewHolder {

    private ImageView optionImg;
    View view;
    public UserOptionsViewHolder(View itemView) {
        super(itemView);

        view = itemView;
        optionImg = itemView.findViewById(R.id.optionsRowImage);
    }
    public void bindData(UserOptions options, Context context)
    {
        Picasso.with(context).load(options.getOptionImg())
                .resize(200,200)
                .centerCrop()
                .into(optionImg);
    }
}
