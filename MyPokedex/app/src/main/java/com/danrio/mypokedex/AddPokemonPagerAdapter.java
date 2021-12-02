package com.danrio.mypokedex;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AddPokemonPagerAdapter extends FragmentStateAdapter {

    public AddPokemonPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new FragmentAddPokemonType();
            case 2:
                return new FragmentAddPokemonStats();
            case 3:
                return new FragmentAddPokemonPhoto();
            default:
                return new FragmentAddPokemonGeneral();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
