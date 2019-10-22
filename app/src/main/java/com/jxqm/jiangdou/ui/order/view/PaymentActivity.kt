package com.jxqm.jiangdou.ui.order.view

import android.annotation.SuppressLint
import com.jaeger.library.StatusBarUtil
import com.jxqm.jiangdou.R
import com.jxqm.jiangdou.base.BaseDataActivity
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ext.addTextChangedListener
import com.jxqm.jiangdou.ui.order.vm.PaymentViewModel
import com.jxqm.jiangdou.utils.clickWithTrigger
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : BaseDataActivity<PaymentViewModel>() {
    private var mCurrentStatus = PAYMENT_STATUS_ONE
    private var mNeedPayMoney = 200
    override fun getLayoutId(): Int = R.layout.activity_payment

    override fun getEventKey(): Any = Constants.EVENT_PAYMENT

    override fun initView() {
        super.initView()
        StatusBarUtil.setColorNoTranslucent(this, resources.getColor(R.color.colorAccent))

        rlPayOne.clickWithTrigger {
            mCurrentStatus = PAYMENT_STATUS_ONE
            changePaymentStatus()
        }

        rlPayTwo.clickWithTrigger {
            mCurrentStatus = PAYMENT_STATUS_TWO
            changePaymentStatus()
        }

        rlPayThree.clickWithTrigger {
            mCurrentStatus = PAYMENT_STATUS_THREE
            changePaymentStatus()
        }

        rlPayFour.clickWithTrigger {
            mCurrentStatus = PAYMENT_STATUS_FOUR
            changePaymentStatus()
        }

        rlWxPayParent.clickWithTrigger {
            cbSelectWx.isChecked = true
            cbSelectAlipay.isChecked = false
        }

        rlAlipayPayParent.clickWithTrigger {
            cbSelectWx.isChecked = false
            cbSelectAlipay.isChecked = true
        }

        etInputMoney.addTextChangedListener {
            beforeTextChanged { charSequence, i, i2, i3 ->
                resetPaymentStatus()
            }
        }
    }

    private fun changePaymentStatus() {
        resetPaymentStatus()
        when (mCurrentStatus) {
            PAYMENT_STATUS_ONE -> {
                rlPayOne.setBackgroundResource(R.drawable.shape_publish_job_item_checked)
                ivPayOneMoneyCount.setBackgroundResource(R.drawable.icon_douzi_white)
                tvPayOneMoneyCount.setTextColor(resources.getColor(R.color.white))
                tvPayOneMoney.setTextColor(resources.getColor(R.color.white))
                mNeedPayMoney = 200
            }
            PAYMENT_STATUS_TWO -> {
                rlPayTwo.setBackgroundResource(R.drawable.shape_publish_job_item_checked)
                ivPayTwoMoneyCount.setBackgroundResource(R.drawable.icon_douzi_white)
                tvPayTwoMoneyCount.setTextColor(resources.getColor(R.color.white))
                tvPayTwoMoney.setTextColor(resources.getColor(R.color.white))
                mNeedPayMoney = 500
            }
            PAYMENT_STATUS_THREE -> {
                rlPayThree.setBackgroundResource(R.drawable.shape_publish_job_item_checked)
                ivPayThreeMoneyCount.setBackgroundResource(R.drawable.icon_douzi_white)
                tvPayThreeMoneyCount.setTextColor(resources.getColor(R.color.white))
                tvPayThreeMoney.setTextColor(resources.getColor(R.color.white))
                mNeedPayMoney = 800
            }
            PAYMENT_STATUS_FOUR -> {
                rlPayFour.setBackgroundResource(R.drawable.shape_publish_job_item_checked)
                ivPayFourMoneyCount.setBackgroundResource(R.drawable.icon_douzi_white)
                tvPayFourMoneyCount.setTextColor(resources.getColor(R.color.white))
                tvPayFourMoney.setTextColor(resources.getColor(R.color.white))
                mNeedPayMoney = 1000

            }
        }
        tvPaying.text = "支付¥$mNeedPayMoney"
    }

    @SuppressLint("ResourceAsColor")
    private fun resetPaymentStatus() {
        rlPayOne.setBackgroundResource(R.drawable.shape_publish_job_item_default)
        rlPayTwo.setBackgroundResource(R.drawable.shape_publish_job_item_default)
        rlPayThree.setBackgroundResource(R.drawable.shape_publish_job_item_default)
        rlPayFour.setBackgroundResource(R.drawable.shape_publish_job_item_default)
        ivPayOneMoneyCount.setBackgroundResource(R.drawable.icon_douzi_yellow)
        ivPayTwoMoneyCount.setBackgroundResource(R.drawable.icon_douzi_yellow)
        ivPayThreeMoneyCount.setBackgroundResource(R.drawable.icon_douzi_yellow)
        ivPayFourMoneyCount.setBackgroundResource(R.drawable.icon_douzi_yellow)

        tvPayOneMoneyCount.setTextColor(resources.getColor(R.color.text_publish_sex))
        tvPayOneMoney.setTextColor(resources.getColor(R.color.text_publish_sex))
        tvPayTwoMoneyCount.setTextColor(resources.getColor(R.color.text_publish_sex))
        tvPayTwoMoney.setTextColor(resources.getColor(R.color.text_publish_sex))
        tvPayThreeMoneyCount.setTextColor(resources.getColor(R.color.text_publish_sex))
        tvPayThreeMoney.setTextColor(resources.getColor(R.color.text_publish_sex))
        tvPayFourMoneyCount.setTextColor(resources.getColor(R.color.text_publish_sex))
        tvPayFourMoney.setTextColor(resources.getColor(R.color.text_publish_sex))
    }

    companion object {
        const val PAYMENT_STATUS_ONE = 0x01
        const val PAYMENT_STATUS_TWO = 0x02
        const val PAYMENT_STATUS_THREE = 0x03
        const val PAYMENT_STATUS_FOUR = 0x04

    }
}