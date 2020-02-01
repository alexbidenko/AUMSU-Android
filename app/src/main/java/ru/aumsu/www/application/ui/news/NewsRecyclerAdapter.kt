package ru.aumsu.www.application.ui.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.news_card.view.*
import ru.aumsu.www.application.R
import ru.aumsu.www.application.models.NewsModel


class NewsRecyclerAdapter : RecyclerView.Adapter<NewsRecyclerAdapter.Holder>() {

    private val newsData = Gson().fromJson<Array<NewsModel>>(data, Array<NewsModel>::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false))
    }

    override fun getItemCount(): Int = newsData.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(newsData[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.title
        private val photo = itemView.photo
        private val content = itemView.content

        fun bind(data: NewsModel) {
            title.text = data.title
            Glide.with(photo).load(data.image).into(photo)
            content.text = data.content

            itemView.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
                startActivity(itemView.context, browserIntent, null)
            }
        }
    }

    companion object {
        const val data = """[{"title":"День памяти неизвестного солдата","content":"Вечная память героям! Курсанты Морского колледжа ГМУ им. адм. Ф. Ф. Ушакова возложили цветы и почтили память неизвестного солдата минутой молчания.","image":"http://www.aumsu.ru/media/k2/items/cache/4693ba70fe7ccf8c5461d5f42e16af89_XS.jpg","url":"http://www.aumsu.ru/component/k2/item/558-%D0%B4%D0%B5%D0%BD%D1%8C-%D0%BF%D0%B0%D0%BC%D1%8F%D1%82%D0%B8-%D0%BD%D0%B5%D0%B8%D0%B7%D0%B2%D0%B5%D1%81%D1%82%D0%BD%D0%BE%D0%B3%D0%BE-%D1%81%D0%BE%D0%BB%D0%B4%D0%B0%D1%82%D0%B0"},{"title":"День рождения Жукова","content":"Студенты Транспортного колледжа почтили минутой молчания память великого полководца, четырежды Героя Советского Союза Георгия Жукова. 1 декабря маршалу исполнилось бы 123 года.","image":"http://www.aumsu.ru/media/k2/items/cache/7c8a032eb1fd580d4ed0e1065f1e3688_XS.jpg","url":"http://www.aumsu.ru/component/k2/item/557-%D0%B4%D0%B5%D0%BD%D1%8C-%D1%80%D0%BE%D0%B6%D0%B4%D0%B5%D0%BD%D0%B8%D1%8F-%D0%B6%D1%83%D0%BA%D0%BE%D0%B2%D0%B0"},{"title":"Общегородское родительское собрание","content":"Студенты Транспортного и Морского колледжей выступили на общегородском родительском собрании «Вместе планируем профессиональный успех». На этом мероприятии выпускники 9-х классов и их родители получили возможность приглядеться и оценить специальности всех девяти техникумов и колледжей Новороссийска.","image":"http://www.aumsu.ru/media/k2/items/cache/2660be2b2df02c41fc17abdbfc676d66_XS.jpg","url":"http://www.aumsu.ru/component/k2/item/556-%D0%BE%D0%B1%D1%89%D0%B5%D0%B3%D0%BE%D1%80%D0%BE%D0%B4%D1%81%D0%BA%D0%BE%D0%B5-%D1%80%D0%BE%D0%B4%D0%B8%D1%82%D0%B5%D0%BB%D1%8C%D1%81%D0%BA%D0%BE%D0%B5-%D1%81%D0%BE%D0%B1%D1%80%D0%B0%D0%BD%D0%B8%D0%B5"},{"title":"Присяга кадетов","content":"Ещё не курсанты, но уже и не школьники. Это про новоиспечённых кадетов из новороссийской школы №12. В ГМУ им. Ф.Ф.Ушакова больше 30 пятиклассников в торжественной обстановке приняли присягу и получили кадетские погоны. Теперь студенты первого курса Морского колледжа станут для детей кураторами и друзьями.","image":"http://www.aumsu.ru/media/k2/items/cache/7dff099a65b026895942a872c251a542_XS.jpg","url":"http://www.aumsu.ru/component/k2/item/555-%D0%BF%D1%80%D0%B8%D1%81%D1%8F%D0%B3%D0%B0-%D0%BA%D0%B0%D0%B4%D0%B5%D1%82%D0%BE%D0%B2"},{"title":"Кинологи в Университете","content":"Совсем не злая собака!В Ушаковке 27 ноября прошла необычная лекция. Сама тема, рассказывающая о роле кинологической службы в раскрытии преступлений, вполне предсказуема для курсантов, изучающих таможенное дело, а вот форма проведения была явно не стандартной.","image":"http://www.aumsu.ru/media/k2/items/cache/16e33b8fd7ad7ca58b03c6bbca1f0b81_XS.jpg","url":"http://www.aumsu.ru/component/k2/item/554-%D0%BA%D0%B8%D0%BD%D0%BE%D0%BB%D0%BE%D0%B3%D0%B8-%D0%B2-%D1%83%D0%BD%D0%B8%D0%B2%D0%B5%D1%80%D1%81%D0%B8%D1%82%D0%B5%D1%82%D0%B5"}]"""
    }
}