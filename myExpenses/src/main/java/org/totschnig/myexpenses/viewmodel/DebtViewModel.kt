package org.totschnig.myexpenses.viewmodel

import android.app.Application
import android.content.ContentUris
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import app.cash.copper.flow.mapToList
import app.cash.copper.flow.mapToOne
import app.cash.copper.flow.observeQuery
import kotlinx.coroutines.flow.collect
import org.threeten.bp.LocalDate
import org.totschnig.myexpenses.provider.DatabaseConstants.KEY_AMOUNT
import org.totschnig.myexpenses.provider.DatabaseConstants.KEY_DATE
import org.totschnig.myexpenses.provider.DatabaseConstants.KEY_DEBT_ID
import org.totschnig.myexpenses.provider.DatabaseConstants.KEY_ROWID
import org.totschnig.myexpenses.provider.TransactionProvider
import org.totschnig.myexpenses.util.epoch2LocalDate
import org.totschnig.myexpenses.viewmodel.data.Debt

class DebtViewModel(application: Application) : ContentResolvingAndroidViewModel(application) {

    fun saveDebt(debt: Debt): LiveData<Unit> = liveData(context = coroutineContext()) {
        emit(repository.saveDebt(debt))
    }

    fun loadDebt(debtId: Long): LiveData<Debt> = liveData {
        contentResolver.observeQuery(
            singleDebtUri(debtId),
            null,
            null,
            null,
            null
        ).mapToOne {
            Debt.fromCursor(it)
        }.collect(this::emit)
    }

    private fun singleDebtUri(debtId: Long) =
        ContentUris.withAppendedId(TransactionProvider.DEBTS_URI, debtId)

    fun loadTransactions(debtId: Long, initialDebt: Long): LiveData<List<Transaction>> =
        liveData {
            var runningTotal = initialDebt
            contentResolver.observeQuery(
                uri = TransactionProvider.TRANSACTIONS_URI,
                projection = arrayOf(KEY_ROWID, KEY_DATE, "-$KEY_AMOUNT"),
                selection = "$KEY_DEBT_ID = ?",
                selectionArgs = arrayOf(debtId.toString()),
                sortOrder = "$KEY_DATE ASC"
            ).mapToList {
                val amount = it.getLong(2)
                runningTotal += amount
                Transaction(it.getLong(0), epoch2LocalDate(it.getLong(1)), amount, runningTotal)
            }.collect(this::emit)
        }

    fun deleteDebt(debtId: Long): LiveData<Boolean> =
        liveData(context = coroutineContext()) {
            emit(contentResolver.delete(singleDebtUri(debtId), null, null) == 1)
        }

    data class Transaction(
        val id: Long,
        val date: LocalDate,
        val amount: Long?,
        val runningTotal: Long
    )
}