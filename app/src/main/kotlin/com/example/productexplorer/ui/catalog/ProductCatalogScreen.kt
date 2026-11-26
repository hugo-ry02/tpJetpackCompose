package com.example.productexplorer.ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
                text = product.brand,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${product.price} € • ★ ${product.rating}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onFavoriteClick) {
                Text(
                    text = if (isFavorite) {
                        "Retirer des favoris"
                    } else {
                        "Ajouter aux favoris"
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onClick) {
                Text(text = "Voir le produit")
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
        favoriteProductIds = uiState.favoriteProductIds,
        onFavoriteClick = viewModel::onFavoriteClick,
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
    favoriteProductIds: List<Int>,
    onFavoriteClick: (Int) -> Unit,
    onProductClick: (ProductUi) -> Unit,
    modifier: Modifier = Modifier
) {
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
            Button(
                onClick = onToggleStockFilter
            ) {
                Text(
                    text = if (showOnlyInStock) {
                        "Afficher tous les produits"
                    } else {
                        "Afficher uniquement les produits en stock"
                    }
                )
            }
        }

        item {
            Text(
                text = "${products.size} produit(s) affiché(s)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        item {
            CategoryRow(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategoryClick = onCategoryClick
            )
        }

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
            favoriteProductIds = listOf(1),
            onFavoriteClick = {},
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
            products = sampleProducts(),
            categories = sampleCategories(),
            searchQuery = "audio",
            onSearchQueryChange = {},
            selectedCategory = "Audio",
            onCategoryClick = {},
            showOnlyInStock = true,
            onToggleStockFilter = {},
            favoriteProductIds = listOf(2),
            onFavoriteClick = {},
            onProductClick = {}
        )
    }
}
