import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';

type EntityResponseType = HttpResponse<IModelLettreReponse>;
type EntityArrayResponseType = HttpResponse<IModelLettreReponse[]>;

@Injectable({ providedIn: 'root' })
export class ModelLettreReponseService {
  public resourceUrl = SERVER_API_URL + 'api/model-lettre-reponses';

  constructor(protected http: HttpClient) {}

  create(modelLettreReponse: IModelLettreReponse): Observable<EntityResponseType> {
    return this.http.post<IModelLettreReponse>(this.resourceUrl, modelLettreReponse, { observe: 'response' });
  }

  update(modelLettreReponse: IModelLettreReponse): Observable<EntityResponseType> {
    return this.http.put<IModelLettreReponse>(this.resourceUrl, modelLettreReponse, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModelLettreReponse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModelLettreReponse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
