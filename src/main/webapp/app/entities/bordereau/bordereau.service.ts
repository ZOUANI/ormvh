import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBordereau } from 'app/shared/model/bordereau.model';

type EntityResponseType = HttpResponse<IBordereau>;
type EntityArrayResponseType = HttpResponse<IBordereau[]>;

@Injectable({ providedIn: 'root' })
export class BordereauService {
  public resourceUrl = SERVER_API_URL + 'api/bordereaus';

  constructor(protected http: HttpClient) {}

  create(bordereau: IBordereau): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bordereau);
    return this.http
      .post<IBordereau>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bordereau: IBordereau): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bordereau);
    return this.http
      .put<IBordereau>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBordereau>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBordereau[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bordereau: IBordereau): IBordereau {
    const copy: IBordereau = Object.assign({}, bordereau, {
      dateBordereaux:
        bordereau.dateBordereaux != null && bordereau.dateBordereaux.isValid() ? bordereau.dateBordereaux.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateBordereaux = res.body.dateBordereaux != null ? moment(res.body.dateBordereaux) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bordereau: IBordereau) => {
        bordereau.dateBordereaux = bordereau.dateBordereaux != null ? moment(bordereau.dateBordereaux) : null;
      });
    }
    return res;
  }
}
