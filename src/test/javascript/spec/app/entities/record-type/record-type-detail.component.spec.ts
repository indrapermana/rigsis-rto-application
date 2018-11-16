/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RtoAppTestModule } from '../../../test.module';
import { RecordTypeDetailComponent } from 'app/entities/record-type/record-type-detail.component';
import { RecordType } from 'app/shared/model/record-type.model';

describe('Component Tests', () => {
    describe('RecordType Management Detail Component', () => {
        let comp: RecordTypeDetailComponent;
        let fixture: ComponentFixture<RecordTypeDetailComponent>;
        const route = ({ data: of({ recordType: new RecordType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [RecordTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RecordTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecordTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.recordType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
