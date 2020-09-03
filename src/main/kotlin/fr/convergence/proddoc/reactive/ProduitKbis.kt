package fr.convergence.proddoc.reactive

import fr.convergence.proddoc.libs.model.Produit
import fr.convergence.proddoc.libs.service.ProduitCache
import fr.convergence.proddoc.services.rest.client.KbisService
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory.getLogger
import org.eclipse.microprofile.reactive.messaging.Incoming
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject


@ApplicationScoped
public class ProduitKbis(@Inject var cache: ProduitCache) {

    companion object {
        private val LOG: Logger = getLogger(Produit::class.java)
    }

    @Incoming("parametre")
    fun ecoute(produit: Produit) {
        LOG.info("Réception du produit : $produit")
        cache.addParameter(produit)
    }

    @Incoming("produitkbis")
    fun ecouteKbis(produit: Produit) {
        LOG.info("Réception demande Kbis : $produit")
        val kbisSrv: KbisService? = null
        val kbisPDF = kbisSrv?.getPDFbyNumGestion(produit.valeur)
        LOG.info("Taille du Kbis : ${kbisPDF?.size}")
    }

    //@TODO un outgoing sur le meme topic kbisOK ou kbisKO
}