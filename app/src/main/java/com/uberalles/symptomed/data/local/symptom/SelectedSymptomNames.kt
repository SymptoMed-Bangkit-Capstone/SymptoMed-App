package com.uberalles.symptomed.data.local.symptom

object SelectedSymptomNames{
    var selectedSymptomList = listOf<String>()

    val symptomListSave = listOf(
        "Gatal",
        "Ruam Kulit",
        "Erupsi Kulit Nodal",
        "Bersin Terus Menerus",
        "Gemetaran",
        "Panas Dingin atau Meriang",
        "Nyeri Sendi",
        "Sakit Perut",
        "Keasaman",
        "Bisul Di Lidah",
        "Penyusutan Otot",
        "Muntah",
        "Perih Saat Buang Air Kecil",
        "Bercak Pada Urin",
        "Kelelahan",
        "Penambahan Berat Badan",
        "Kecemasan",
        "Tangan dan Kaki Dingin",
        "Perubahan Suasana Hati",
        "Penurunan Berat Badan",
        "Kegelisahan",
        "Kelesuan",
        "Bercak di Tenggorokan",
        "Kadar Gula Tidak Teratur",
        "Batuk",
        "Demam Tinggi",
        "Mata Cekung atau Kantung Mata",
        "Sesak Napas",
        "Berkeringat",
        "Dehidrasi",
        "Gangguan Pencernaan",
        "Sakit Kepala",
        "Kulit Kekuningan",
        "Urin Gelap",
        "Mual",
        "Kehilangan Selera Makan",
        "Nyeri di Belakang Mata",
        "Sakit Punggung",
        "Sembelit",
        "Sakit Perut 1",
        "Diare",
        "Demam Ringan",
        "Urin Kuning",
        "Menguningnya Mata",
        "Gagal Hati Akut",
        "Kelebihan Cairan",
        "Pembengkakan Perut",
        "Kelenjar Getah Bening Membengkak",
        "Indra Perasa Pahit",
        "Penglihatan Kabur dan Terdistorsi",
        "Berdahak",
        "Iritasi Tenggorokan",
        "Mata Merah",
        "Tekanan Sinus",
        "Pilek",
        "Penyumbatan",
        "Nyeri Dada",
        "Kelemahan Pada Tungkai",
        "Jantung Berdetak Cepat",
        "Nyeri Saat Buang Air Besar",
        "Nyeri di Daerah Anus",
        "Tinja Berdarah",
        "Iritasi Pada Anus",
        "Sakit Leher",
        "Pusing",
        "Kram",
        "Memar",
        "Kegemukan",
        "Kaki Bengkak",
        "Pembuluh Darah Membengkak",
        "Wajah dan Mata Sembab",
        "Tiroid Yang Membesar",
        "Kuku Rapuh",
        "Ekstremitas Bengkak",
        "Rasa Lapar yang Berlebihan",
        "Hubungan di Luar Nikah",
        "Bibir Kering dan Kesemutan",
        "Ucapan Cadel",
        "Sakit Lutut",
        "Nyeri Sendi Panggul",
        "Otot Melemah",
        "Leher Kaku",
        "Sendi Bengkak",
        "Kekakuan Gerakan",
        "Gerakan Berputar",
        "Kehilangan Keseimbangan",
        "Tremor",
        "Kelemahan Satu Sisi Tubuh",
        "Kehilangan Penciuman",
        "Rasa Tidak Nyaman Pada Kandung Kemih",
        "Bau Urin yang Busuk",
        "Keluar Urin Terus Menerus",
        "Lewatnya Gas",
        "Gatal dari Dalam Tubuh",
        "Tifus",
        "Depresi",
        "Sifat Lekas Marah",
        "Nyeri Otot",
        "Perubahan Tingkat Kesadaran",
        "Bintik Bintik Merah di Sekujur Tubuh",
        "Sakit Perut 2",
        "Menstruasi yang Tidak Normal",
        "Patch Diskromik",
        "Mata Berair",
        "Nafsu Makan Meningkat",
        "Sering Buang Air Kecil",
        "Penyakit Turunan Keluarga",
        "Dahak Berlendir dan Kental",
        "Dahak Berwarna Seperti Karat",
        "Kurang Konsentrasi",
        "Gangguan Penglihatan",
        "Menerima Transfusi Darah",
        "Menerima Suntikan yang Tidak Steril",
        "Koma",
        "Pendarahan Perut",
        "Kembung",
        "Riwayat Konsumsi Alkohol",
        "Kelebihan Cairan 1",
        "Darah Dalam Dahak",
        "Pembuluh Darah Menonjol di Betis",
        "Palpitasi",
        "Kesakitan Saat Berjalan",
        "Jerawat Berisi Nanah",
        "Komedo",
        "Berteriak Teriak",
        "Pengelupasan Kulit",
        "Kulit Menjadi Ke Abu-abuan",
        "Penyok Kecil di Kuku",
        "Kuku Inflamasi",
        "Melepuh",
        "Sakit Merah di Sekitar Hidung",
        "Kerak Kuning Keluar"
    )

    fun getSelectedSymptomList(): IntArray {
        val dataArray = IntArray(132)
        val symptomsListProcess = selectedSymptomList.toMutableList()

        for (h in symptomsListProcess.indices) {
            if (symptomsListProcess[h] == "Sakit Perut") {
                symptomsListProcess.add(h + 1, "Sakit Perut 1")
                symptomsListProcess.add(h + 2, "Sakit Perut 2")
                break
            }
        }

        for (h in symptomsListProcess.indices) {
            if (symptomsListProcess[h] == "Kelebihan Cairan") {
                symptomsListProcess.add(h + 1, "Kelebihan Cairan 1")
                break
            }
        }

        println(symptomsListProcess)

        for (i in symptomListSave.indices) {
            for (j in symptomsListProcess.indices) {
                if (symptomsListProcess[j] == symptomListSave[i]) {
                    dataArray[i] = 1
                    break
                }
                else {
                    dataArray[i] = 0
                }
            }
        }
        return dataArray
    }

}