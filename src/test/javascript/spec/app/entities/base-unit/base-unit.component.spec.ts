/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RtoAppTestModule } from '../../../test.module';
import { BaseUnitComponent } from 'app/entities/base-unit/base-unit.component';
import { BaseUnitService } from 'app/entities/base-unit/base-unit.service';
import { BaseUnit } from 'app/shared/model/base-unit.model';

describe('Component Tests', () => {
    describe('BaseUnit Management Component', () => {
        let comp: BaseUnitComponent;
        let fixture: ComponentFixture<BaseUnitComponent>;
        let service: BaseUnitService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [RtoAppTestModule],
                declarations: [BaseUnitComponent],
                providers: []
            })
                .overrideTemplate(BaseUnitComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BaseUnitComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BaseUnitService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BaseUnit(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.baseUnits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
