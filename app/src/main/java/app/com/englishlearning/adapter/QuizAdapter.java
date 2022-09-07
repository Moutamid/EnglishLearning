package app.com.englishlearning.adapter;


import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import app.com.englishlearning.R;
import app.com.englishlearning.model.ImgUrlModel;
import app.com.englishlearning.model.WordsModel;
import app.com.englishlearning.network.APIClient;
import app.com.englishlearning.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//adapter is a class which we used to show list of data for example this adapter is used to show all the compaings in the project
public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.View_Holder> {
    private QuizAdapter.OnitemClickListener mListener;
    Context context;


    public interface OnitemClickListener {
        void OnItemClick(int position);//

        void onaddclick(int position);

    }

    public void setOnItemClick(QuizAdapter.OnitemClickListener listener) {
        mListener = listener;
    }

    LayoutInflater layoutInflater;
    List<WordsModel> users;


    public QuizAdapter(Context ctx, List<WordsModel> users) {
        this.layoutInflater = LayoutInflater.from(ctx);
        this.users = users;
        context = ctx;

    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.quiz_options_design, parent, false);//here we define what view is our adapter showing here we are showing row_all_compaings view which you can see in res->layout
        return new View_Holder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
//        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
//        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
//        holder.cardView.setBackgroundColor(randomAndroidColor);
        holder.title.setText(users.get(position).getTitle());//here we are defining our data what we have to show it is coming from tha api


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class View_Holder extends RecyclerView.ViewHolder {
        TextView title;
        CardView cardView;


        public View_Holder(@NonNull View itemView, final QuizAdapter.OnitemClickListener listener) {
            super(itemView);
            //here we are initializing our components that were in the roww_all_views
            title = (TextView) itemView.findViewById(R.id.tvOption);
            cardView = itemView.findViewById(R.id.optionCard);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                            listener.OnItemClick(position);
                        }
                    }
                }
            });


        }
    }

}


