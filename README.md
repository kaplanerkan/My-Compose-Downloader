# My-Compose-Downloader
Liste icinde verilen dosyalari sunucudan download eder


# Açıklamalar ve Önemli Noktalar:

##ViewModel: 		Tüm indirme ve kaydetme mantığı FileDownloadViewModel içinde yönetilir. Bu, UI'yi (Compose) iş mantığından ayırır ve test edilebilirliği artırır.

## mutableStateListOf: 	downloadStatuses listesi, Compose'un gözlemleyebileceği ve değişikliklere tepki verebileceği özel bir listedir. Bir durum güncellendiğinde, UI otomatik olarak yeniden çizilir.

## Coroutines: 		viewModelScope.launch(Dispatchers.IO) ile ağ işlemleri ve dosya kaydetme işlemleri ana iş parçacığını (UI thread) engellemeden arka planda yapılır. Dispatchers.IO, bu tür işlemler için optimize edilmiştir.

## Hata Yönetimi: 		try-catch blokları, ağ hataları veya dosya yazma hataları gibi olası sorunları yakalar ve kullanıcıya uygun bir hata mesajı gösterir.


# Dosya Kaydetme:

			## context.filesDir: 	Uygulamanın özel depolama alanını kullanır. Bu, uygulamanız silindiğinde dosyaların da silineceği anlamına gelir. 
						## Eğer dosyaların kalıcı olmasını istiyorsanız, Environment.getExternalStoragePublicDirectory() gibi harici depolama
 						alanlarını kullanmanız gerekir (ancak bu, Android 10 ve üzeri için ek izinler ve yaklaşımlar gerektirir).

			## Dosyaları "images" adlı bir alt dizine kaydeder.

			## Base64 ile decode edilen veri önce bir ByteArray'e, sonra bir Bitmap'e dönüştürülür. Ardından, 
						Bitmap.compress metodu ile PNG formatında dosyaya kaydedilir.

#İlerleme Göstergesi: LinearProgressIndicator, indirme ilerlemesini görsel olarak gösterir.

# İptal Etme (İsteğe Bağlı): cancelDownload fonksiyonu, devam eden bir indirmeyi iptal etmek için bir örnek sunar. Bunu UI'nize bir "İptal" butonu ekleyerek kullanabilirsiniz.

# Base64 Decoding: 	Base64.decode(base64Content, Base64.DEFAULT) satırı, sunucudan gelen Base64 kodlu metni byte dizisine çevirir.

# Sunucu Tarafı (Önemli): Bu kod, sunucunuzun doğru şekilde yapılandırıldığını varsayar. Sunucunuzun:

			## - İstenen dosyaları Base64 kodlu metin olarak göndermesi gerekir.
			## - Content-Type başlığını text/plain olarak ayarlaması önemlidir. Farklı bir Content-Type gelirse indirme işlemi hata verecektir.
			## - Dosya boyutunu Content-Length başlığında doğru bir şekilde belirtmesi gerekir.


Bu örnek, temel bir yapı sunar. İhtiyaçlarınıza göre daha da geliştirebilirsiniz. Örneğin:


			# - Hata İşleme: Daha detaylı hata işleme mekanizmaları ekleyebilirsiniz (örneğin, yeniden deneme, farklı hata türleri için farklı işlemler).

			# - İndirme Kuyruğu: Birden fazla indirme isteğini sıraya koyabilir ve yönetebilirsiniz.

			# - Ağ Durumu Kontrolü: İndirme işlemine başlamadan önce ağ bağlantısının olup olmadığını kontrol edebilirsiniz.

			# - İzinler: Harici depolama kullanıyorsanız, gerekli izinleri (örneğin, WRITE_EXTERNAL_STORAGE) istemeyi unutmayın. 
				Android 10+ için kapsamlı depolama (scoped storage) kurallarına dikkat edin.

			# - Testler: ViewModel ve Retrofit arabirimi için birim testleri yazarak kodunuzun doğruluğunu sağlayabilirsiniz.

			# - Daha İyi UI: Kullanıcı deneyimini iyileştirmek için daha gelişmiş bir arayüz tasarlayabilirsiniz 
				(örneğin, indirme hızını gösterme, daha iyi hata mesajları, indirme geçmişi).


Bu kapsamlı örnek, Jetpack Compose ile çoklu dosya indirme işlemini nasıl gerçekleştirebileceğinizi ve indirme durumunu kullanıcıya nasıl gösterebileceğinizi detaylı bir şekilde açıklamaktadır. Umarım bu, projeniz için iyi bir başlangıç noktası olur!
