## Overview
15 Puzzle (atau disebut juga Game of Fifteen) adalah sebuah <i>sliding puzzle</i> yang berukuran 4x4 ubin, 
dengan salah satu ubin dihilangkan. 15 Puzzle terdiri dari 15 buah ubin bernomor 1 hingga 15. Posisi awal
puzzle adalah acak, dan tujuan dari puzzle adalah menyusun puzzle menjadi berposisi sebagai berikut.
```
 1  2  3  4
 5  6  7  8
 9 10 11 12
13 14 15  0
```
Catatan: 0 menunjukkan ubin kosong.

Program ini adalah sebuah program yang bertujuan untuk menyelesaikan sembarang puzzle yang dapat diselesaikan.

## Prerequisites
Program ini ditulis dengan bahasa Java, sehingga pengguna harus memiliki JRE (Java Runtime Environment) 
atau JDK (Java Development Kit).

## Usage
Program ini dapat di-<i>compile</i> ulang dengan menjalankan <i>command</i> berikut pada terminal
di folder root.
<pre>
javac -d bin src/*.java
</pre>

Program kemudian dapat dijalankan dengan <i>command</i> sebagai berikut.
<pre>
java -cp bin Main
</pre>

Program dapat menerima puzzle dari file pada folder `test` maupun membuat sebuah puzzle secara acak.
Contoh isi file yang dapat diterima sebagai input puzzle adalah sebagai berikut.
```
1 3 6 4
5 2 8 11
13 9 7 12
0 10 14 15
```
File diasumsikan selalu sesuai dengan ketentuan, yaitu (1) ubin kosong disimbolkan dengan angka 0,
(2) isi file berbentuk matriks 4 x 4, dan (3) ubin yang bukan nol berada dalam rentang 1 hingga 15
tanpa ada angka yang berulang.

## Program Output

Program memiliki beberapa output, secara terurut sebagai berikut.
1. Nilai fungsi KURANG(i) untuk i = 1..16
2. Nilai jumlah semua KURANG(i) untuk i = 1..16 ditambah dengan X
3. Puzzle yang diinput
4. Pergerakan puzzle dan hasil pergerakan puzzle hingga solusi, beserta 
   jumlah simpul yang dibangkitkan jika dapat diselesaikan
5. Pesan gagal jika tidak dapat diselesaikan
6. Solving time jika dapat diselesaikan, yaitu waktu yang dibutuhkan untuk hanya menyelesaikan puzzle
7. Elapsed time, yaitu waktu yang dibutuhkan untuk melakukan perhitungan dan output

## Author
Wesly Giovano (13520071)

Teknik Informatika - Institut Teknologi Bandung