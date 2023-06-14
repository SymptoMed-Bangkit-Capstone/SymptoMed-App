package com.uberalles.symptomed.utilities

import com.uberalles.symptomed.R
import com.uberalles.symptomed.data.local.entity.FaqEntity
import com.uberalles.symptomed.data.local.entity.TeamEntity

object Data {
    fun generateMembers(): ArrayList<TeamEntity> {
        val members = ArrayList<TeamEntity>()

        members.add(
            TeamEntity(
                R.drawable.photo_anugrah_cahya,
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

    fun generateFaq(): ArrayList<FaqEntity> {
        val faq = ArrayList<FaqEntity>()

        faq.add(
            FaqEntity(
                "Apa itu SymptoMed?",
                "SymptoMed adalah aplikasi seluler yang dirancang untuk membantu pengguna memahami gejala yang mereka alami dan memberikan informasi medis yang akurat. Aplikasi ini menggunakan algoritma pembelajaran mesin untuk menganalisis gejala yang diinputkan pengguna dan memberikan rekomendasi yang personal, termasuk saran obat, rekomendasi vitamin dan nutrisi, serta nasihat gaya hidup."
            )
        )

        faq.add(
            FaqEntity(
                "Bagaimana cara kerja SymptoMed?",
                "Pengguna dapat memasukkan gejala yang mereka alami ke dalam aplikasi, dan algoritma pembelajaran mesin SymptoMed akan menganalisis data tersebut untuk memberikan rekomendasi yang personal. Aplikasi ini akan memberikan informasi terpercaya, saran obat yang sesuai, rekomendasi vitamin, dan perubahan gaya hidup."
            )
        )

        faq.add(
            FaqEntity(
                "Apakah SymptoMed menggantikan nasihat medis profesional?",
                "Tidak, SymptoMed bukan pengganti nasihat medis profesional. Aplikasi ini dimaksudkan sebagai alat pendukung untuk membantu pengguna memahami gejala yang mereka alami dan membuat keputusan yang lebih terinformasi tentang kesehatan mereka. Selalu disarankan untuk berkonsultasi dengan tenaga medis yang berkualifikasi untuk panduan dan pengobatan yang personal."
            )
        )

        faq.add(
            FaqEntity(
                "Seberapa akurat rekomendasi yang diberikan oleh SymptoMed?",
                "SymptoMed bertujuan untuk memberikan rekomendasi yang akurat berdasarkan gejala yang diinputkan pengguna dan informasi yang tersedia. Namun, perlu diingat bahwa ada batasan dan variasi dalam pelaporan gejala, sehingga akurasi rekomendasi dapat bervariasi. Pengguna harus menggunakan pertimbangan mereka sendiri dan berkonsultasi dengan tenaga medis ketika diperlukan."
            )
        )

        faq.add(
            FaqEntity(
                "Apakah informasi pribadi saya aman di SymptoMed?",
                "SymptoMed mengambil privasi dan keamanan secara serius. Aplikasi ini menerapkan langkah-langkah untuk melindungi data pengguna dan mengikuti praktik standar industri. Namun, pengguna harus tetap menjaga privasi mereka sendiri dan mengambil langkah-langkah yang diperlukan, seperti menggunakan kata sandi yang aman dan menjaga keamanan perangkat mereka."
            )
        )

        faq.add(
            FaqEntity(
                "Dapatkah SymptoMed mendiagnosis kondisi medis yang serius?",
                "SymptoMed dapat memberikan saran berdasarkan gejala yang dilaporkan, tetapi bukan pengganti diagnosis medis yang tepat. Selalu disarankan untuk berkonsultasi dengan tenaga medis untuk evaluasi dan diagnosis menyeluruh kondisi medis yang serius."
            )
        )

        faq.add(
            FaqEntity(
                "Dapatkah SymptoMed digunakan dalam situasi darurat?",
                "Ya dan tidak, SymptoMed tidak dirancang untuk situasi darurat. Tetapi SymptoMed dapat memberikan informasi cepat secara real time dalam situasi darurat tertentu, tetap saja pengguna harus segera mencari bantuan medis yang sesuai dengan menghubungi layanan darurat atau mengunjungi fasilitas kesehatan terdekat."
            )
        )

        faq.add(
            FaqEntity(
                "Dapatkah SymptoMed digunakan untuk anak-anak atau lansia?",
                "SymptoMed mempunyai batas umur dari remaja hingga dewasa. Untuk saat ini data untuk balita hingga anak-anak belum sempurna sehingga bisa mengurangi akurasi prediksi diagnosis."
            )
        )

        return faq
    }
}