package com.tsyapa.rates.ui.screens.rates

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tsyapa.rates.R
import com.tsyapa.rates.ui.screens.rates.data.model.Rate
import com.tsyapa.rates.utils.TextWatcher
import com.tsyapa.rates.utils.loadUrl
import com.tsyapa.rates.utils.showKeyboard
import java.util.*

const val IMAGE_URL = "https://tvcostaverde.com.br/signage1852/modules/currencies/currency-flags/"
const val SVG_SUFFIX = ".svg"

class RateAdapter(
    private val onBaseAmountChanged: (amount: Double) -> Unit,
    private val onBaseCurrencyChanged: (newCurrencyPosition: Int) -> Unit
): RecyclerView.Adapter<RateAdapter.RateViewHolder>() {

    private var recyclerView: RecyclerView? = null

    private var rates: MutableList<Rate>? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rate, parent, false))
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rate = rates!![position]
        holder.itemView.tag = position

        holder.ivFlag.loadUrl(IMAGE_URL + rate.currency + SVG_SUFFIX)

        holder.tvCurrency.text = rate.currency

        val amount = rate.amount
        if (amount > 0) {
            holder.etAmount.setText(if(amount % 1 == 0.0) amount.toString()
                else String.format(Locale.getDefault(), "%.2f", amount))
        } else {
            holder.etAmount.setText("")
        }
        holder.etAmount.setSelection(holder.etAmount.text.length)
    }

    override fun getItemCount(): Int = if(rates != null) rates!!.size else 0

    fun setRates(newRates: MutableList<Rate>){
        if(rates == null) {
            rates = newRates
            notifyDataSetChanged()
        } else {
            notifyItemRangeChanged(1, rates!!.size - 1)
        }
    }

    private fun moveItemToTop(positionFrom: Int) {
        if (positionFrom != 0) {
            onBaseCurrencyChanged(positionFrom)
            notifyItemRangeChanged(0, positionFrom + 1)
            notifyItemMoved(positionFrom, 0)
        }
    }

    inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, TextWatcher {

        val ivFlag: ImageView = itemView.findViewById(R.id.iv_item_rate_flag)
        val tvCurrency: TextView = itemView.findViewById(R.id.tv_item_rate_currency)
        var etAmount: EditText = itemView.findViewById(R.id.et_item_rate_amount)

        init {
            itemView.setOnClickListener(this)
            etAmount.addTextChangedListener(this)
            etAmount.onFocusChangeListener =
                OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus && itemView.tag as Int != 0) {
                        onClick(itemView)
                    }
                }
        }

        override fun onClick(view: View) {
            moveItemToTop(view.tag as Int)
            recyclerView?.scrollToPosition(0)
            etAmount.post { showKeyboard(etAmount.context, etAmount) }
        }

        override fun afterTextChanged(editable: Editable) {
            val position = itemView.tag as Int
            recyclerView?.let {
                if (!it.isComputingLayout) {
                    if (position == 0) {
                        try{
                            val amount = if (editable.isNotEmpty()) editable.toString().toDouble() else 0.0
                            onBaseAmountChanged(amount)
                            notifyItemRangeChanged(1, rates!!.size - 1)
                        } catch (e: NumberFormatException){}
                    } else {
                        moveItemToTop(position)
                    }
                }
            }
        }
    }
}