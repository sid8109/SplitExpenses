package com.example.splitexpenses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.abs

class NameViewModel: ViewModel() {
    var names = MutableLiveData<LinkedHashMap<String, Double>>(linkedMapOf("" to 0.0))
    var spentOn = MutableLiveData<MutableList<String>>(mutableListOf(""))
    var transactions = MutableLiveData<MutableList<String>>(mutableListOf(""))
    var finaltrans = MutableLiveData<MutableList<String>>(mutableListOf(""))
    var flag = false
    fun addMember(memberName: String) {
        if(!flag) {
            names.value!!.clear()
            flag = true
        }
        names.value?.put(memberName, 0.0)
    }

    fun spent(spentAmount: Double, spentBy: String): Boolean {
        names.value!![spentBy] = names.value!![spentBy]!! + spentAmount
        if(spentOn.value!!.size == 1) {
            return false
        }
        val perPerson = (spentAmount * 1.0) / (spentOn.value!!.size - 1)
        for(i in spentOn.value!!) {
            if(i != "") {
                names.value!![i] = names.value!![i]!! + perPerson * (-1)
            }
        }

        var transaction = "$spentBy paid $spentAmount for"
        var k = 0
        for(i in spentOn.value!!) {
            if(i == "") continue
            if(k == spentOn.value!!.size - 1) {
                transaction = "$transaction $i"
                break
            }
            transaction = "$transaction $i,"
            k++
        }
        transactions.value?.add(transaction)
        return true
    }

    fun calculate() {
        var name = mutableListOf<String>()
        var money = mutableListOf<Double>()
        for(i in names.value!!) {
            name.add(i.key)
            money.add(i.value)
        }
        finaltrans.value!!.clear()
        helperCalculate(name, money)
    }

    fun helperCalculate(name: List<String>, money: MutableList<Double>) {
        val maxIdx = money.indexOf(money.max())
        val minIdx = money.indexOf(money.min())
        if(money[maxIdx] <1.0 && money[minIdx] >-1.0) {
            return
        }
        var paisa = 0.0
        if(money[maxIdx] == -1 * money[minIdx]) {
            paisa = money[minIdx]
            money[maxIdx] = 0.0
            money[minIdx] = 0.0
        } else if(money[maxIdx] > -1 * money[minIdx]) {
            money[maxIdx] = money[maxIdx] + money[minIdx]
            paisa = money[minIdx]
            money[minIdx] = 0.0
        } else {
            money[minIdx] = money[minIdx] + money[maxIdx]
            paisa = money[maxIdx]
            money[maxIdx] = 0.0
        }
        finaltrans.value!!.add("${name[minIdx]} has to pay ${abs(paisa).toInt()} to ${name[maxIdx]}")
        helperCalculate(name, money)
    }
}