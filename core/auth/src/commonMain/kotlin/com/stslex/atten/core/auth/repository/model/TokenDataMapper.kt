package com.stslex.atten.core.auth.repository.model

import com.stslex.atten.core.core.result.Mapping
import com.stslex.atten.core.network.api.model.TokenResponseModel

object TokenDataMapper : Mapping<TokenResponseModel, TokenDataModel> {

    override fun invoke(data: TokenResponseModel): TokenDataModel = with(data) {
        TokenDataModel(
            uuid = uuid,
            email = email,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}