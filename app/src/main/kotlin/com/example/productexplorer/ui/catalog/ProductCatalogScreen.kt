package com.example.productexplorer.ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.productexplorer.model.ProductUi
import com.example.productexplorer.model.sampleCategories
import com.example.productexplorer.model.sampleProducts
import com.example.productexplorer.ui.components.CategoryChip
import com.example.productexplorer.ui.theme.ProductExplorerTheme

@Composable
fun CategoryRow(
    categories: List<String>,
    selectedCategory: String?,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            CategoryChip(
                label = category,
                isSelected = category == selectedCategory,
                onClick = { onCategoryClick(category) }
            )
        }
    }
}

@Composable
fun CatalogFilterRow(
    showOnlyInStock: Boolean,
    onToggleStockFilter: () -> Unit,
    showOnlyFavorites: Boolean,
    favoritesCount: Int,
    onToggleFavoritesFilter: () -> Unit,
    priceSortOrder: PriceSortOrder,
    onCyclePriceSort: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sortLabel = when (priceSortOrder) {
        PriceSortOrder.NONE -> "Prix : défaut"
        PriceSortOrder.ASCENDING -> "Prix ↑"
        PriceSortOrder.DESCENDING -> "Prix ↓"
    }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            if (showOnlyInStock) {
                Button(onClick = onToggleStockFilter) {
                    Text(text = "En stock ✓")
                }
            } else {
                OutlinedButton(onClick = onToggleStockFilter) {
                    Text(text = "En stock")
                }
            }
        }

        item {
            if (showOnlyFavorites) {
                Button(onClick = onToggleFavoritesFilter) {
                    Text(text = "Favoris ($favoritesCount) ✓")
                }
            } else {
                OutlinedButton(onClick = onToggleFavoritesFilter) {
                    Text(text = "Favoris ($favoritesCount)")
                }
            }
        }

        item {
            if (priceSortOrder != PriceSortOrder.NONE) {
                Button(onClick = onCyclePriceSort) {
                    Text(text = sortLabel)
                }
            } else {
                OutlinedButton(onClick = onCyclePriceSort) {
                    Text(text = sortLabel)
                }
            }
        }
    }
}

@Composable
fun ProductListItem(
    product: ProductUi,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${product.brand} • ${product.category}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "${product.price} € • ★ ${product.rating}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (product.stock > 0) "En stock (${product.stock})" else "Rupture",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (product.stock > 0) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                if (isFavorite) {
                    Button(onClick = onFavoriteClick) {
                        Text(text = "Retirer favori")
                    }
                } else {
                    OutlinedButton(onClick = onFavoriteClick) {
                        Text(text = "Favori")
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onClick) {
                    Text(text = "Voir le produit")
                }
            }
        }
    }
}

@Composable
fun EmptyCatalogCard(
    onResetFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Aucun produit trouvé",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Essayez de modifier votre recherche ou vos filtres actifs.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onResetFilters) {
                Text(text = "Réinitialiser les filtres")
            }
        }
    }
}

@Composable
fun ProductCatalogContainer(
    onProductClick: (ProductUi) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductCatalogViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ProductCatalogScreen(
        products = uiState.products,
        categories = uiState.categories,
        searchQuery = uiState.searchQuery,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        selectedCategory = uiState.selectedCategory,
        onCategoryClick = viewModel::onCategoryClick,
        showOnlyInStock = uiState.showOnlyInStock,
        onToggleStockFilter = viewModel::onToggleStockFilter,
        showOnlyFavorites = uiState.showOnlyFavorites,
        onToggleFavoritesFilter = viewModel::onToggleFavoritesFilter,
        priceSortOrder = uiState.priceSortOrder,
        onCyclePriceSort = viewModel::onCyclePriceSort,
        favoriteProductIds = uiState.favoriteProductIds,
        onFavoriteClick = viewModel::onFavoriteClick,
        onResetFilters = viewModel::onResetFilters,
        onProductClick = onProductClick,
        modifier = modifier
    )
}

@Composable
fun ProductCatalogScreen(
    products: List<ProductUi>,
    categories: List<String>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCategory: String?,
    onCategoryClick: (String) -> Unit,
    showOnlyInStock: Boolean,
    onToggleStockFilter: () -> Unit,
    showOnlyFavorites: Boolean,
    onToggleFavoritesFilter: () -> Unit,
    priceSortOrder: PriceSortOrder,
    onCyclePriceSort: () -> Unit,
    favoriteProductIds: List<Int>,
    onFavoriteClick: (Int) -> Unit,
    onResetFilters: () -> Unit,
    onProductClick: (ProductUi) -> Unit,
    modifier: Modifier = Modifier
) {
    val hasActiveFilters = searchQuery.isNotBlank() ||
        selectedCategory != null ||
        showOnlyInStock ||
        showOnlyFavorites ||
        priceSortOrder != PriceSortOrder.NONE

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Catalogue produits",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = {
                    Text(text = "Rechercher un produit")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            CatalogFilterRow(
                showOnlyInStock = showOnlyInStock,
                onToggleStockFilter = onToggleStockFilter,
                showOnlyFavorites = showOnlyFavorites,
                favoritesCount = favoriteProductIds.size,
                onToggleFavoritesFilter = onToggleFavoritesFilter,
                priceSortOrder = priceSortOrder,
                onCyclePriceSort = onCyclePriceSort
            )
        }

        item {
            CategoryRow(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategoryClick = onCategoryClick
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "${products.size} produit(s) affiché(s)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
                if (hasActiveFilters) {
                    OutlinedButton(onClick = onResetFilters) {
                        Text(text = "Réinitialiser")
                    }
                }
            }
        }

        if (products.isEmpty()) {
            item {
                EmptyCatalogCard(onResetFilters = onResetFilters)
            }
        } else {
            items(
                items = products,
                key = { product -> product.id }
            ) { product ->
                ProductListItem(
                    product = product,
                    isFavorite = favoriteProductIds.contains(product.id),
                    onFavoriteClick = {
                        onFavoriteClick(product.id)
                    },
                    onClick = {
                        onProductClick(product)
                    }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    name = "Catalogue - Light Theme"
)
@Composable
fun ProductCatalogScreenLightPreview() {
    ProductExplorerTheme(darkTheme = false) {
        ProductCatalogScreen(
            products = sampleProducts(),
            categories = sampleCategories(),
            searchQuery = "",
            onSearchQueryChange = {},
            selectedCategory = null,
            onCategoryClick = {},
            showOnlyInStock = false,
            onToggleStockFilter = {},
            showOnlyFavorites = false,
            onToggleFavoritesFilter = {},
            priceSortOrder = PriceSortOrder.NONE,
            onCyclePriceSort = {},
            favoriteProductIds = listOf(1),
            onFavoriteClick = {},
            onResetFilters = {},
            onProductClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Catalogue - Dark Theme"
)
@Composable
fun ProductCatalogScreenDarkPreview() {
    ProductExplorerTheme(darkTheme = true) {
        ProductCatalogScreen(
            products = sampleProducts().filter { it.category == "Audio" }.sortedBy { it.price },
            categories = sampleCategories(),
            searchQuery = "audio",
            onSearchQueryChange = {},
            selectedCategory = "Audio",
            onCategoryClick = {},
            showOnlyInStock = false,
            onToggleStockFilter = {},
            showOnlyFavorites = false,
            onToggleFavoritesFilter = {},
            priceSortOrder = PriceSortOrder.ASCENDING,
            onCyclePriceSort = {},
            favoriteProductIds = listOf(2),
            onFavoriteClick = {},
            onResetFilters = {},
            onProductClick = {}
        )
    }
}
