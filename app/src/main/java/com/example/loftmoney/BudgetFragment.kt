package com.example.loftmoney


import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loftmoney.adapter.ItemClickListener
import com.example.loftmoney.adapter.ItemsAdapter
import com.example.loftmoney.item_model.ItemModel
import com.example.loftmoney.web.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_budget.*


const val LOFT_TAG = "LOFT"
const val EXTRA_KEY = "extra key"
const val ADD_EXPENSE_ITEM = "expense"
const val ADD_INCOME_ITEM = "income"


class BudgetFragment : Fragment(), ItemClickListener, ActionMode.Callback {

    private val disposable = CompositeDisposable()
    private val items = ArrayList<ItemModel>()
    private val adapter = ItemsAdapter(items, listener = this)
    private var actionMode: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_budget, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(activity, LinearLayoutManager.VERTICAL)
        )
        recyclerView.adapter = adapter

        /**
         *  Get data from server based on the current fragment
         */
        arguments?.takeIf { it.containsKey(FRAGMENT_KEY) }?.apply {

            loadItems()
        }

        /**
         *  Swipe Refresh
         */
        swipe_refresh.setOnRefreshListener {
            loadItems()
            swipe_refresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        loadItems()
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    private fun loadItems() {
        when (arguments?.get(FRAGMENT_KEY)) {
            0 -> loadItemsFromServer("expense")
            1 -> loadItemsFromServer("income")
        }
    }

    private fun loadItemsFromServer(type: String) {
        val sharedPrefs = activity?.getSharedPreferences(
            getString(R.string.app_name),
            Context.MODE_PRIVATE)

        val authToken = sharedPrefs!!.getString(AUTH_TOKEN_KEY, "no-token-received")
        val responseFromApi = ApiService.createApiService.getItems(type, authToken!!)

        val chargeList = ArrayList<ItemModel>()

        disposable.add(responseFromApi
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                    dataList ->
                        for (dataItem in dataList) {
                            chargeList.add(
                                ItemModel(
                                    dataItem
                                )
                            )
                        }
                        adapter.setNewData(chargeList)
                },
                {
                    it.localizedMessage
                }
            )
        )
    }

    override fun onItemClick(position: Int) {
        if (actionMode != null) {
            adapter.toggleItem(position)
            actionMode!!.setTitle(getString(R.string.selected, adapter.countSelected().toString()))
        }
    }

    override fun onItemLongClick(position: Int) {
        if (actionMode == null) {
            activity?.startActionMode(this)
        }
        adapter.toggleItem(position)
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.menu.remove_menu) {

        }
        return true
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        actionMode = mode
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        MenuInflater(activity).inflate(R.menu.remove_menu, menu)
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
        adapter.clearAllSelections()
    }

    private fun removeItem(itemId: Int) {
        val responseFromApi =
            ApiService.createApiService.removeItem(itemId)

        disposable.add(responseFromApi
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                Toast.makeText(activity, "Item deleted successfully", Toast.LENGTH_SHORT).show()
            },
                {
                    Toast.makeText(activity, "Transaction failed", Toast.LENGTH_SHORT).show()
                })
        )
    }

}

