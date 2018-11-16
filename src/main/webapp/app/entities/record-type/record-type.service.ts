import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecordType } from 'app/shared/model/record-type.model';

type EntityResponseType = HttpResponse<IRecordType>;
type EntityArrayResponseType = HttpResponse<IRecordType[]>;

@Injectable({ providedIn: 'root' })
export class RecordTypeService {
    public resourceUrl = SERVER_API_URL + 'api/record-types';

    constructor(private http: HttpClient) {}

    create(recordType: IRecordType): Observable<EntityResponseType> {
        return this.http.post<IRecordType>(this.resourceUrl, recordType, { observe: 'response' });
    }

    update(recordType: IRecordType): Observable<EntityResponseType> {
        return this.http.put<IRecordType>(this.resourceUrl, recordType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRecordType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRecordType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
