package com.danrio.mypokedex;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class EditPokemonPagerAdapter extends FragmentStateAdapter {

    public EditPokemonPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new FragmentEditPokemonType();
            case 2:
                return new FragmentEditPokemonStats();
            case 3:
                return new FragmentEditPokemonPhoto();
            default:
                return new FragmentEditPokemonGeneral();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
