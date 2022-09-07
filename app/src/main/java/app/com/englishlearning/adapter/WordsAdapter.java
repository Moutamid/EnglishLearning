package app.com.englishlearning.adapter;


import static com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC;

import android.content.Context;
import android.content.Intent;
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
import app.com.englishlearning.WordsActivity;
import app.com.englishlearning.model.ImgUrlModel;
import app.com.englishlearning.model.WordsModel;
import app.com.englishlearning.network.APIClient;
import app.com.englishlearning.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//adapter is a class which we used to show list of data for example this adapter is used to show all the compaings in the project
public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.View_Holder> {
    private WordsAdapter.OnitemClickListener mListener;
    Context context;
    TextToSpeech textToSpeech;

    public interface OnitemClickListener {
        void OnItemClick(int position);//

        void onaddclick(int position);

    }

    public void setOnItemClick(WordsAdapter.OnitemClickListener listener) {
        mListener = listener;
    }

    LayoutInflater layoutInflater;
    List<WordsModel> users;


    public WordsAdapter(Context ctx, List<WordsModel> users) {
        this.layoutInflater = LayoutInflater.from(ctx);
        this.users = users;
        context = ctx;
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if (i != TextToSpeech.ERROR) {
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_words, parent, false);//here we define what view is our adapter showing here we are showing row_all_compaings view which you can see in res->layout
        return new View_Holder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
        WordsModel currentItem = users.get(position);
        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        holder.MainCard.setCardBackgroundColor(randomAndroidColor);
        holder.title.setText(users.get(position).getTitle());
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        apiService.Register("25707240-9c632bb3ee0bdb5ae65d78d7d", users.get(position).getTitle(), "photo", true).enqueue(new Callback<ImgUrlModel>() {
            @Override
            public void onResponse(Call<ImgUrlModel> call, Response<ImgUrlModel> response) {
                if (response.body().getHits().size()!=0) {
                    Glide.with(context)
                            .asBitmap()
                            .load(response.body().getHits().get(0).getPreviewURL())
                            .apply(new RequestOptions()
                                    .placeholder(R.color.lighterGrey)
                                    .error(R.color.lighterGrey)
                            )
                            .diskCacheStrategy(AUTOMATIC)
                            .into(holder.img);
                }
                else{
                    Glide.with(context)
                            .asBitmap()
                            .load(context.getResources().getString(R.string.defualtUrl))
                            .apply(new RequestOptions()
                                    .placeholder(R.color.lighterGrey)
                                    .error(R.color.lighterGrey)
                            )
                            .diskCacheStrategy(AUTOMATIC)
                            .into(holder.img);
                }
            }

            @Override
            public void onFailure(Call<ImgUrlModel> call, Throwable t) {
                Toast.makeText(context, "bad", Toast.LENGTH_SHORT).show();
            }
        });

        holder.speaker.setImageResource(R.drawable.ic_speaker_svgrepo_com__3_);
        holder.speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "Any Text to Speak";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    textToSpeech.speak(holder.title.getText().toString(), TextToSpeech.QUEUE_FLUSH, null, null);
                } else {
                    textToSpeech.speak(holder.title.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class View_Holder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img, speaker;
        CardView cardView, MainCard;


        public View_Holder(@NonNull View itemView, final WordsAdapter.OnitemClickListener listener) {
            super(itemView);
            //here we are initializing our components that were in the roww_all_views
            title = (TextView) itemView.findViewById(R.id.tvWord);
            img = itemView.findViewById(R.id.imgWord);
            speaker = itemView.findViewById(R.id.imgSpeaker);
            MainCard = itemView.findViewById(R.id.cardViews);


        }
    }

    public String getUrl(String name) {
        final String[] url = {""};
        ApiService apiService = APIClient.getClient().create(ApiService.class);
        apiService.Register("25707240-9c632bb3ee0bdb5ae65d78d7d", name, "photo", true).enqueue(new Callback<ImgUrlModel>() {
            @Override
            public void onResponse(Call<ImgUrlModel> call, Response<ImgUrlModel> response) {
                if (response.body() != null) {
                    url[0] = response.body().getHits().get(0).getPreviewURL();
                }
            }

            @Override
            public void onFailure(Call<ImgUrlModel> call, Throwable t) {
                Toast.makeText(context, "bad", Toast.LENGTH_SHORT).show();
            }
        });
        return url[0];
    }
}


