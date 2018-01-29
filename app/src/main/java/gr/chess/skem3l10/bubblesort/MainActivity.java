package gr.chess.skem3l10.bubblesort;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView randomTextView,afterSortTextView,initialValueTextView;
    private EditText inputN,inputR;

    private int[] array;
    
    private static final String RANDOM_NUMBERS_PREFS_KEY ="your key for random numbers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
    }

    private void initializeViews(){
        this.randomTextView =this.findViewById(R.id.randomed_numbers);
        this.afterSortTextView=this.findViewById(R.id.after_sort);
        this.initialValueTextView=this.findViewById(R.id.initialValueTextView);
        this.inputN = this.findViewById(R.id.input_n_EditText);
        this.inputR = this.findViewById(R.id.input_r_EditText);
    }


    public void confirm(View view){
        // take the n from the user (the length of the array)
        int n = Integer.parseInt(this.inputN.getText().toString());
        // check if the n is greater than zero, so you can create an array of numbers
        if (n>=0){
            this.array=new int[n];
        }else{
            // if not, then create the default array with length of 8
            this.array=new int[8];
        }

        // take r from the user (the maximum of a number value from 0 to r)
        int r = Integer.parseInt(this.inputR.getText().toString());
        if(r>=0){
            // lets populate the array with sorted numbers with 'r' values
            populateArray(this.array,r);
        }else{
            // lets populate the array with a default of r=3
            populateArray(this.array,3);
        }

        // now that we have an array lets show the sorted values
        String values = this.getArrayString(this.array);
        this.initialValueTextView.setText(values);


    }

    private void populateArray(int[] array, int r /* this value will be the r that the user gave, the array length is n*/){
        Random random = new Random();
        for (int i=0; i<array.length; i++){
            int arrayIndex = i;
            array[i]=random.nextInt(r);
        }
    }


    public void save(View view){
        // get the default sharedpreferences file from your 
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        
        // get what was in the shared preferences saved before
        String savedBefore = preferences.getString(RANDOM_NUMBERS_PREFS_KEY,"");
        
        // get the new line of random numbers, as showed in the textview, so you can save them later
        String newLineOfRandNums = this.randomTextView.getText().toString();
        
        // do the save
        boolean isSaved = preferences.edit().putString(RANDOM_NUMBERS_PREFS_KEY,savedBefore+newLineOfRandNums+"\n")
                .commit();
        if (isSaved){
            Toast.makeText(this, "saved "+newLineOfRandNums, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "not saved", Toast.LENGTH_SHORT).show();
        }
    }


    /*
    * This method loads the numbers that were stored and displays them in a Toast msg
    * */
    public void load(View view){
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        String storeNumbers = prefs.getString(RANDOM_NUMBERS_PREFS_KEY,"No values to load");
        Toast.makeText(this, storeNumbers, Toast.LENGTH_SHORT).show();
    }

    public void clear(View view){
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        boolean isCleared = prefs.edit().clear().commit();
        if (isCleared){
            Toast.makeText(this, "File cleared", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "File is not cleared", Toast.LENGTH_SHORT).show();
        }
    }


    public void doRandom(View view){
        // shuffle array before sort
        this.array = shuffleArray(this.array);
        // show the new shuffled array to textview
        this.randomTextView.setText(getArrayString(this.array));
    }

    public void doBubble(View view){
        bubbleSort(array);
        this.afterSortTextView.setText(getArrayString(array));
        this.afterSortTextView.setVisibility(View.VISIBLE);
    }

    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        int temp = 0;
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(arr[j-1] > arr[j]){
                    //swap elements
                    temp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    /* Return a string representation of an int[] */
    private String getArrayString(int[] array){
        StringBuilder b = new StringBuilder();
        for (int i=0; i<array.length; i++){
            b.append(" "+array[i]+" ");
        }
        return b.toString();
    }

    // Implementing Fisher–Yates shuffle
    private static int[] shuffleArray(int[] array)
    {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
        return array;
    }
}
