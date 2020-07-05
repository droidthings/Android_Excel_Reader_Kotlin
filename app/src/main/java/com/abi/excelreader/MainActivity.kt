package com.abi.excelreader

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    lateinit var excelAdapter: ExcelAdapter
    lateinit var excelView: RecyclerView
    private val excelListData = arrayListOf<ExcelData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_read)
        setUpToolBar(toolbar)
        readExcelFile()
        if(excelListData.isNotEmpty()){
            setUpRecyclerView()
        }else {
            Toast.makeText(this, "No Items Read", Toast.LENGTH_SHORT).show()
        }
        val searchView = findViewById<SearchView>(R.id.search_item)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                excelAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun setUpRecyclerView() {
        excelView = findViewById(R.id.rv_item)
        excelView.layoutManager = LinearLayoutManager(this)
        excelView.setHasFixedSize(true)
        excelAdapter = ExcelAdapter(excelListData)
        excelView.adapter = excelAdapter
    }

    private fun readExcelFile() {
        try {
            val inputStream: InputStream = assets.open("data_sample.xls")

            val workFileSystem = POIFSFileSystem(inputStream)
            val workBookExcel = HSSFWorkbook(workFileSystem)
            val sheets: Int = workBookExcel.numberOfSheets
            for (sheet in 0 until sheets - 1) {
                val sheetExcel = workBookExcel.getSheetAt(sheet)
                val sheetRows = sheetExcel.iterator()
                while (sheetRows.hasNext()) {
                    val currentRow = sheetRows.next()
                    if(currentRow.rowNum != 0){
                        val cellsInRow = currentRow.iterator()
                        var excelData = ExcelData()
                        while (cellsInRow.hasNext()) {
                            val currentCell = cellsInRow.next()
                            if(currentCell.columnIndex != 0){
                                when(currentCell.columnIndex){
                                    1 -> excelData.name = currentCell.stringCellValue
                                    2 -> excelData.age = currentCell.numericCellValue.toInt()
                                    3 -> excelData.course = currentCell.stringCellValue
                                    4 -> excelData.track = currentCell.stringCellValue
                                    5 -> excelData.grade = currentCell.numericCellValue.toInt()
                                    6 -> excelData.location = currentCell.stringCellValue
                                }
                            }
                        }
                        excelListData.add(excelData)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("MainActivity", e.toString())
        }
    }

    private fun setUpToolBar(toolbar: Toolbar?) {
        setSupportActionBar(toolbar)
        toolbar?.title = "Read Data"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
