package com.melhkptn.searchapp.presentation.view

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.melhkptn.searchapp.R
import com.melhkptn.searchapp.domain.model.response.Results
import com.melhkptn.searchapp.util.convertDate
import com.melhkptn.searchapp.util.downloadImage
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment() {

    private var url: String? = null

    override fun getLayoutRes(): Int = R.layout.fragment_detail

    override fun initView() {
        super.initView()

        arguments?.let {
            val detail = it.getParcelable<Results>("ResultItem")
            detail?.let {
                imageViewDetail.downloadImage(detail.artworkUrl100, requireContext())
                tvDetailName.text = detail.trackName
                tvDetailPrice.text = detail.trackPrice.toString()
                tvDetailReleaseDate.text = detail.releaseDate.convertDate().toString()
                if (detail.kind != "feature-movie")
                    tvDescription.text = detail.description
                else
                    tvDescription.text = detail.longDescription

                url = detail.previewUrl
            }
        }

        buttonPreview.setOnClickListener {
            url?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            } ?: kotlin.run {
                Toast.makeText(context, "No preview source", Toast.LENGTH_LONG).show()
            }

        }

    }

}