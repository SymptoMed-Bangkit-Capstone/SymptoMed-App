package com.uberalles.symptomed.utilities

import com.uberalles.symptomed.R
import com.uberalles.symptomed.data.local.entity.TeamEntity

object TeamData {
    fun generateDummyMembers(): ArrayList<TeamEntity> {
        val members = ArrayList<TeamEntity>()

        members.add(
            TeamEntity(
                R.drawable.ic_account,
                "Anugrah Cahya Kautsar",
            "Machine Learning",
                "",
                ""
            )
        )

        members.add(
            TeamEntity(
                R.drawable.photo_fauzan,
                "Fauzan Nauvally",
                "Machine Learning",
                "",
                ""
            )
        )

        members.add(
            TeamEntity(
                R.drawable.photo_rio,
                "Rio Bastian",
                "Machine Learning",
                "",
                ""
            )
        )

        members.add(
            TeamEntity(
                R.drawable.ic_account,
                "Ilmi Fatha Nur Ihsan",
                "Cloud Computing",
                "",
                ""
            )
        )

        members.add(
            TeamEntity(
                R.drawable.photo_jhonson_2,
                "Jhonson Saputra",
                "Cloud Computing",
                "",
                ""
            )
        )

        members.add(
            TeamEntity(
                R.drawable.photo_arizona,
                "Arizona Adi Pradana",
                "Mobile Development",
                "https://github.com/arizonaadipradana/",
                "https://www.linkedin.com/in/arizonapradana/"
            )
        )

        return members
    }
}