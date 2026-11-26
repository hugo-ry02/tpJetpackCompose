package com.example.productexplorer.ui.catalog

import androidx.lifecycle.ViewModel
import com.example.productexplorer.model.ProductUi
import com.example.productexplorer.model.sampleCategories
import com.example.productexplorer.model.sampleProducts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class PriceSortOrder {
    NONE,
    ASCENDING,
    DESCENDING
}

data class ProductCatalogUiState(
    val products: List<ProductUi> = emptyList(),
    val categories: List<String> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val showOnlyInStock: Boolean = false,
    val showOnlyFavorites: Boolean = false,
    val priceSortOrder: PriceSortOrder = PriceSortOrder.NONE,
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

    private fun filterAndSortProducts(
        searchQuery: String,
        selectedCategory: String?,
        showOnlyInStock: Boolean,
        showOnlyFavorites: Boolean,
        favoriteProductIds: List<Int>,
        priceSortOrder: PriceSortOrder
    ): List<ProductUi> {
        val filtered = allProducts.filter { product ->
            val matchesSearch =
                product.title.contains(searchQuery, ignoreCase = true) ||
                    product.brand.contains(searchQuery, ignoreCase = true) ||
                    product.category.contains(searchQuery, ignoreCase = true)

            val matchesCategory =
                selectedCategory == null || product.category.equals(selectedCategory, ignoreCase = true)

            val matchesStock =
                !showOnlyInStock || product.stock > 0

            val matchesFavorites =
                !showOnlyFavorites || favoriteProductIds.contains(product.id)

            matchesSearch && matchesCategory && matchesStock && matchesFavorites
        }

        return when (priceSortOrder) {
            PriceSortOrder.NONE -> filtered
            PriceSortOrder.ASCENDING -> filtered.sortedBy { it.price }
            PriceSortOrder.DESCENDING -> filtered.sortedByDescending { it.price }
        }
    }

    fun onSearchQueryChange(newValue: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = newValue,
                products = filterAndSortProducts(
                    searchQuery = newValue,
                    selectedCategory = currentState.selectedCategory,
                    showOnlyInStock = currentState.showOnlyInStock,
                    showOnlyFavorites = currentState.showOnlyFavorites,
                    favoriteProductIds = currentState.favoriteProductIds,
                    priceSortOrder = currentState.priceSortOrder
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
                products = filterAndSortProducts(
                    searchQuery = currentState.searchQuery,
                    selectedCategory = newSelectedCategory,
                    showOnlyInStock = currentState.showOnlyInStock,
                    showOnlyFavorites = currentState.showOnlyFavorites,
                    favoriteProductIds = currentState.favoriteProductIds,
                    priceSortOrder = currentState.priceSortOrder
                )
            )
        }
    }

    fun onToggleStockFilter() {
        _uiState.update { currentState ->
            val newShowOnlyInStock = !currentState.showOnlyInStock
            currentState.copy(
                showOnlyInStock = newShowOnlyInStock,
                products = filterAndSortProducts(
                    searchQuery = currentState.searchQuery,
                    selectedCategory = currentState.selectedCategory,
                    showOnlyInStock = newShowOnlyInStock,
                    showOnlyFavorites = currentState.showOnlyFavorites,
                    favoriteProductIds = currentState.favoriteProductIds,
                    priceSortOrder = currentState.priceSortOrder
                )
            )
        }
    }

    fun onToggleFavoritesFilter() {
        _uiState.update { currentState ->
            val newShowOnlyFavorites = !currentState.showOnlyFavorites
            currentState.copy(
                showOnlyFavorites = newShowOnlyFavorites,
                products = filterAndSortProducts(
                    searchQuery = currentState.searchQuery,
                    selectedCategory = currentState.selectedCategory,
                    showOnlyInStock = currentState.showOnlyInStock,
                    showOnlyFavorites = newShowOnlyFavorites,
                    favoriteProductIds = currentState.favoriteProductIds,
                    priceSortOrder = currentState.priceSortOrder
                )
            )
        }
    }

    fun onCyclePriceSort() {
        _uiState.update { currentState ->
            val nextSort = when (currentState.priceSortOrder) {
                PriceSortOrder.NONE -> PriceSortOrder.ASCENDING
                PriceSortOrder.ASCENDING -> PriceSortOrder.DESCENDING
                PriceSortOrder.DESCENDING -> PriceSortOrder.NONE
            }
            currentState.copy(
                priceSortOrder = nextSort,
                products = filterAndSortProducts(
                    searchQuery = currentState.searchQuery,
                    selectedCategory = currentState.selectedCategory,
                    showOnlyInStock = currentState.showOnlyInStock,
                    showOnlyFavorites = currentState.showOnlyFavorites,
                    favoriteProductIds = currentState.favoriteProductIds,
                    priceSortOrder = nextSort
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
                favoriteProductIds = newFavoriteIds,
                products = filterAndSortProducts(
                    searchQuery = currentState.searchQuery,
                    selectedCategory = currentState.selectedCategory,
                    showOnlyInStock = currentState.showOnlyInStock,
                    showOnlyFavorites = currentState.showOnlyFavorites,
                    favoriteProductIds = newFavoriteIds,
                    priceSortOrder = currentState.priceSortOrder
                )
            )
        }
    }

    fun onResetFilters() {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = "",
                selectedCategory = null,
                showOnlyInStock = false,
                showOnlyFavorites = false,
                priceSortOrder = PriceSortOrder.NONE,
                products = allProducts
            )
        }
    }
}
