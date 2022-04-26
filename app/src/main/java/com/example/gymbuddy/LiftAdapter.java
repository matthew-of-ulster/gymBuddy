package com.example.gymbuddy;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class LiftAdapter extends ArrayAdapter<Lift> {
    int height;
    DBHandler db;
    public LiftAdapter(Context context, ArrayList<Lift> lifts) {
        super(context, 0, lifts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        // Get the data item for this position
        Lift lift = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lift, parent, false);
        }
        // Lookup view for data population
        TextView liftName = (TextView) convertView.findViewById(R.id.liftName);
        TextView liftWeight = (TextView) convertView.findViewById(R.id.liftWeight);
        LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.LL);
        Button passBut = (Button) convertView.findViewById(R.id.liftPassBut);
        Button failBut = (Button) convertView.findViewById(R.id.liftFailBut);
        // Populate the data into the template view using the data object
        liftName.setText(lift.name);
        liftWeight.setText(format_weight(lift.weight));
        TextView ex1Desc = (TextView) convertView.findViewById(R.id.ex1Desc);
        RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.expandable);
        LinearLayout whole =(LinearLayout)convertView.findViewById(R.id.whole_item);

        relativeLayout.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        relativeLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                        relativeLayout.setVisibility(View.GONE);

                        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        relativeLayout.measure(widthSpec, heightSpec);
                        height = relativeLayout.getMeasuredHeight()/8;
                        return true;
                    }
                });


        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (relativeLayout.getVisibility() == View.GONE) {
                    ex1Desc.setText(MainActivity.deriveWeights(liftWeight.getText().toString()));
                    expand(relativeLayout, height);
                } else {
                    collapse(relativeLayout);
                }
            }
        });
        passBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double weight = Double.parseDouble(liftWeight.getText().toString());


                //boolean add = lift.past_lifts.add(new past_lift(true, weight));

                weight+=lift.increment;
                liftWeight.setText(format_weight(weight));
                String rWeight = weight+"";
                String rInc = lift.increment+"";


                db = new DBHandler(getContext());
                db.updateLift(lift.name, lift.name, rWeight, rInc);
            }
        });
        failBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lift.weight = Double.parseDouble(liftWeight.getText().toString());
                lift.past_lifts.add(new PastLift(false,lift.weight));
                Intent intent = new Intent(getContext(), EditExcercise.class);

                intent.putExtra("name",lift.name);
                intent.putExtra("weight",lift.weight);
                intent.putExtra("inc",lift.increment);

                getContext().startActivity(intent);
            }
        });



        if(!lift.visible){
            whole.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    private String format_weight(double weight) {
        String rString = "";
        if (weight - (int) weight == 0) {
            rString+= (int)weight;
        }else{
            rString+= weight;
        }
        return rString;
    }


    private void expand(RelativeLayout layout, int layoutHeight) {
        layout.setVisibility(View.VISIBLE);
        ValueAnimator animator = slideAnimator(layout, 0, layoutHeight);
        animator.start();
    }

    private void collapse(final RelativeLayout layout) {
        int finalHeight = layout.getHeight();
        ValueAnimator mAnimator = slideAnimator(layout, finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                layout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }


    private ValueAnimator slideAnimator(final RelativeLayout layout, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
                layoutParams.height = value;
                layout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }


}