package com.example.productexplorer.ui.catalog

import androidx.lifecycle.ViewModel
import com.example.productexplorer.model.ProductUi
import com.example.productexplorer.model.sampleCategories
import com.example.productexplorer.model.sampleProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProductCatalogUiState(
    val products: List<ProductUi> = emptyList(),
    val categories: List<String> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val showOnlyInStock: Boolean = false,
    val favoriteProductIds: List<Int> = emptyList()
)

class ProductCatalogViewModel : ViewModel() {

    private val allProducts: List<ProductUi> = sampleProducts()
    private val allCategories: List<String> = sampleCategories()

    private val _uiState = MutableStateFlow(
        ProductCatalogUiState(
            products = allProducts,
            categories = allCategories
        )
    )

    val uiState: StateFlow<ProductCatalogUiState> = _uiState.asStateFlow()

    private fun filterProducts(
        searchQuery: String,
        selectedCategory: String?,
        showOnlyInStock: Boolean
    ): List<ProductUi> {
        return allProducts.filter { product ->
            val matchesSearch =
                product.title.contains(searchQuery, ignoreCase = true) ||
                    product.brand.contains(searchQuery, ignoreCase = true) ||
                    product.category.contains(searchQuery, ignoreCase = true)

            val matchesCategory =
                selectedCategory == null || product.category.equals(selectedCategory, ignoreCase = true)

            val matchesStock =
                !showOnlyInStock || product.stock > 0

            matchesSearch && matchesCategory && matchesStock
        }
    }

    fun onSearchQueryChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = newValue,
                products = filterProducts(
                    searchQuery = newValue,
                    selectedCategory = currentState.selectedCategory,
                    showOnlyInStock = currentState.showOnlyInStock
                )
            )
        }
    }

    fun onCategoryClick(category: String) {
        _uiState.update { currentState ->
            val newSelectedCategory =
                if (currentState.selectedCategory == category) null else category
            currentState.copy(
                selectedCategory = newSelectedCategory,
                products = filterProducts(
                    searchQuery = currentState.searchQuery,
                    selectedCategory = newSelectedCategory,
                    showOnlyInStock = currentState.showOnlyInStock
                )
            )
        }
    }

    fun onToggleStockFilter() {
        _uiState.update { currentState ->
            val newShowOnlyInStock = !currentState.showOnlyInStock
            currentState.copy(
                showOnlyInStock = newShowOnlyInStock,
                products = filterProducts(
                    searchQuery = currentState.searchQuery,
                    selectedCategory = currentState.selectedCategory,
                    showOnlyInStock = newShowOnlyInStock
                )
            )
        }
    }

    fun onFavoriteClick(productId: Int) {
        _uiState.update { currentState ->
            val newFavoriteIds =
                if (currentState.favoriteProductIds.contains(productId)) {
                    currentState.favoriteProductIds - productId
                } else {
                    currentState.favoriteProductIds + productId
                }

            currentState.copy(
                favoriteProductIds = newFavoriteIds
            )
        }
    }
}
