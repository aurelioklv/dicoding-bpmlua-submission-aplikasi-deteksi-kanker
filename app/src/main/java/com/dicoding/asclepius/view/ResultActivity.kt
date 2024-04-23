package com.dicoding.asclepius.view

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            binding.resultImage.setImageURI(it)
        }

        setupImageClassifier()
        imageClassifierHelper.classifyStaticImage(imageUri)
        supportActionBar?.hide()
    }

    private fun setupImageClassifier() {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let { classifications ->
                            if (classifications.isNotEmpty() && classifications[0].categories.isNotEmpty()) {
                                println(classifications)
                                classifications[0].categories.firstOrNull { it.label == "Cancer" }
                                    ?.let {
                                        if (it.label == "Cancer") {
                                            binding.linearProgressBar.progress =
                                                it.score.times(100).toInt()
                                            val displayText = NumberFormat.getPercentInstance()
                                                .format(it.score) + " ${it.label}"
                                            binding.resultText.text = displayText
                                        }
                                    }
                            } else {
                                binding.resultText.text = ""
                            }
                        }
                    }
                }
            }
        )
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}