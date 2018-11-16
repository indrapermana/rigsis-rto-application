import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWitsService } from 'app/shared/model/wits-service.model';

type EntityResponseType = HttpResponse<IWitsService>;
type EntityArrayResponseType = HttpResponse<IWitsService[]>;

@Injectable({ providedIn: 'root' })
export class WitsServiceService {
    public resourceUrl = SERVER_API_URL + 'api/wits-services';

    constructor(private http: HttpClient) {}

    create(witsService: IWitsService): Observable<EntityResponseType> {
        return this.http.post<IWitsService>(this.resourceUrl, witsService, { observe: 'response' });
    }

    update(witsService: IWitsService): Observable<EntityResponseType> {
        return this.http.put<IWitsService>(this.resourceUrl, witsService, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IWitsService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IWitsService[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
